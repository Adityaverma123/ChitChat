package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserListActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference ref;
    RecyclerView recyclerView;
    CircleImageView profile_image;

    PostViewHolder.UserAdapter userAdapter;
    private List<PostHolder> mUsers;
    TextView textView,profile_username;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.share_menu,menu);
        return super.onCreateOptionsMenu(menu);



    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(UserListActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        if(item.getItemId()==R.id.your_profile)
        {
            Intent intent=new Intent(UserListActivity.this,ProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        profile_username=findViewById(R.id.profile_username);
        profile_image=findViewById(R.id.profile_image);
        recyclerView=findViewById(R.id.recyclerView);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PostHolder holder=dataSnapshot.getValue(PostHolder.class);
                    profile_username.setText(holder.getName());
                    if(holder.getImageurl().equals("default"))
                    {
                        profile_image.setImageResource(R.mipmap.ic_launcher);
                    }
                    else {
                        boolean isadded = holder.getImageurl()!=null;
                        if(isadded)
                        {
                            Glide.with(UserListActivity.this).load(holder.getImageurl()).into(profile_image);
                        }


                    }

                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUsers=new ArrayList<>();

        readUsers();

    }

    private void readUsers() {
        textView=findViewById(R.id.displayChats);
        textView.setVisibility(View.VISIBLE);
        textView.setText("Loading...");

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textView.setVisibility(View.INVISIBLE);
                mUsers.clear();
                for (DataSnapshot snapshot :dataSnapshot.getChildren()) {
                    PostHolder holder = snapshot.getValue(PostHolder.class);

                    if (!holder.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        mUsers.add(holder);

                }

                   else if(holder==null)
                    {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("No chats :(");
                    }


                }
                userAdapter=new PostViewHolder.UserAdapter(mUsers,getApplicationContext());
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
