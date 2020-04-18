package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    DatabaseReference ref;
    EditText message;
    ImageButton imageButton;
    RecyclerView recyclerView;
    MessageAdapter.UserAdapter messageAdapter;
    List<Chat> mChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent=getIntent();
        final String userid=intent.getStringExtra("userid");
        message=findViewById(R.id.inputmessage);
        imageButton=findViewById(R.id.btn_send);
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mChats=new ArrayList<>();
        messageAdapter=new MessageAdapter.UserAdapter(mChats,getApplicationContext());

        recyclerView.setAdapter(messageAdapter);
        readMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),userid);
        ref= FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    PostHolder holder=dataSnapshot.getValue(PostHolder.class);
                    setTitle(holder.getName());
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=message.getText().toString();
                if(msg.equals(""))
                {
                    Toast.makeText(ChatActivity.this,"Message can't be empty",Toast.LENGTH_SHORT).show();

                }
                else
                {

                    sendMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),userid,msg);
                    readMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),userid);
                }
                message.setText("");
            }
        });

    }
    private void sendMessage(String sender,String receiver,String message)
    {
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        FirebaseDatabase.getInstance().getReference().child("chats").push().setValue(hashMap);
    }
    private void readMessage(final String myid, final String userid)
    {

        ref=FirebaseDatabase.getInstance().getReference().child("chats");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChats.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Chat chat=snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                       chat.getReceiver().equals(userid) && chat.getSender().equals(myid))
                    {
                        mChats.add(chat);

                    }
                    messageAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
