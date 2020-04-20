package com.example.chitchat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostViewHolder extends RecyclerView.ViewHolder {
    TextView getName;
    LinearLayout linearLayout;
    CircleImageView imageView;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        getName = itemView.findViewById(R.id.getName);
        linearLayout = itemView.findViewById(R.id.linearLayout);
        imageView=itemView.findViewById(R.id.profile_image);
    }
    public static class UserAdapter extends RecyclerView.Adapter<PostViewHolder>
    {
        private List<PostHolder>mUsers;
        private Context context;

        public UserAdapter(List<PostHolder>mUsers,Context context)
        {
            this.mUsers=mUsers;
            this.context=context;
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.post,parent,false);
            return new PostViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final PostViewHolder holder, int position) {
           final PostHolder holder1=mUsers.get(position);
            holder.getName.setText(holder1.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ChatActivity.class);
                    intent.putExtra("userid",holder1.getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }
}

