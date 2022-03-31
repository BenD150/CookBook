package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser;
    private FirebaseAuth mAuth;
    private EditText email, username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("RegisterActivity", "onCreate has been called for RegisterActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.home);
        registerUser.setOnClickListener(this);

        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        username = (EditText) findViewById(R.id.editTextTextPersonName);
        password = (EditText) findViewById(R.id.editTextTextPassword);
    }

    @Override
    public void onClick(View v){
        registerUser();
    }

    private void registerUser(){
        String sEmail = email.getText().toString().trim();
        String sUsername = username.getText().toString().trim();
        String sPassword = password.getText().toString().trim();

        if(sUsername.isEmpty()){
            username.setError("Username is Required!");
            username.requestFocus();
            return;
        }

        if(sEmail.isEmpty()){
            email.setError("Email is Required!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()){
            email.setError("Please provide a valid Email!");
            email.requestFocus();
            return;
        }

        if(sPassword.isEmpty()){
            password.setError("Password is Required!");
            password.requestFocus();
            return;
        }

        if(sPassword.length()<6){
            password.setError("Password needs to be at least 6 characters!");
            password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Users user = new Users(sUsername, sEmail);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }
}