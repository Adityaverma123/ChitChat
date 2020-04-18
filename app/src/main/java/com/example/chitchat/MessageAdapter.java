package com.example.chitchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.ViewHolder {

    TextView show_message;
    LinearLayout linearLayout;

    public MessageAdapter(@NonNull View itemView) {
        super(itemView);
        show_message = itemView.findViewById(R.id.show_message);
        linearLayout = itemView.findViewById(R.id.linearLayout);
    }
    public static class UserAdapter extends RecyclerView.Adapter<MessageAdapter>
    {
        public static final int msg_left=0;
        public static final int msg_right=1;
        private List<Chat> mChat;
        private Context context;
        public UserAdapter(List<Chat>mChat,Context context)
        {
            this.mChat=mChat;
            this.context=context;
        }

        @NonNull
        @Override
        public MessageAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == msg_right) {
                View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
                return new MessageAdapter(view);
            }
                else
                {
                    View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
                    return new MessageAdapter(view);
                }

        }

        @Override
        public void onBindViewHolder(@NonNull final MessageAdapter holder, int position) {
                Chat chat=mChat.get(position);
                holder.show_message.setText(chat.getMessage());
        }

        @Override
        public int getItemCount() {
            return mChat.size();
        }

        @Override
        public int getItemViewType(int position) {
            if(mChat.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            {
                return msg_right;
            }
            else
            {
                return msg_left;
            }
        }
    }

}

