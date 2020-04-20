package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button button;
    ProgressDialog loadingbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        button = findViewById(R.id.loginButton);
        loadingbar=new ProgressDialog(this);

    }

    public void onClick(View view) {
        if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "Both fields are required", Toast.LENGTH_SHORT).show();
        } else {
            loadingbar.setTitle("Logging in");
            loadingbar.setMessage("Please wait...");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loadingbar.dismiss();
                                login();
                            } else {
                                loadingbar.dismiss();
                                Toast.makeText(LoginActivity.this, "Login failed :(", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }

    }

private void login() {
        Intent intent=new Intent(LoginActivity.this,UserListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        }



}
