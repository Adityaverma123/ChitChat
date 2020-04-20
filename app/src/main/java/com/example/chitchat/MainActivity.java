package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button;
    FirebaseAuth mAuth;
    ProgressDialog loadingbar;
    EditText username,email,password;
    TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.signinButton);
        username=findViewById(R.id.register_username);
        textView=findViewById(R.id.gotoLogin);

        email=findViewById(R.id.register_email);
        password=findViewById(R.id.register_password);

        loadingbar=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            login();
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });




            }
            public void onClick(View view)
            {
                if(email.getText().equals("") || password.getText().equals("") || username.getText().equals(""))
                {
                    Toast.makeText(MainActivity.this,"All fields are required to signin",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingbar.setTitle("Signing up");
                    loadingbar.setMessage("Please wait...");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {

                                         Map map =new HashMap();
                                        map.put("email",email.getText().toString());
                                        map.put("name",username.getText().toString());
                                        map.put("id",mAuth.getCurrentUser().getUid());
                                        map.put("imageurl","default");
                                        FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).updateChildren(map);
                                        //Add on database
                                        loadingbar.dismiss();
                                        login();

                                    }
                                    else
                                    {
                                        loadingbar.dismiss();
                                        Toast.makeText(MainActivity.this,"Account already exists..",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }



private void login() {
        Intent intent=new Intent(MainActivity.this,UserListActivity.class);
        intent.putExtra("name",username.getText().toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        //Main Screen
        }



}




