package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser;
    private FirebaseAuth mAuth;
    private EditText email, username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set view and find Buttons and EditTexts
        Log.i("RegisterActivity", "onCreate has been called for RegisterActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerUser = findViewById(R.id.home);
        registerUser.setOnClickListener(this);

        email = findViewById(R.id.editTextTextEmailAddress);
        username = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
    }

    @Override
    public void onClick(View v){
        // Register the user
        if (v.getId() == R.id.home) {
            if (checkConnection() == false) {
                Toast.makeText(RegisterActivity.this , "No Internet Connection!", Toast.LENGTH_SHORT).show();
            }
            else {
                registerUser();
            }
        }
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

        // Use FirebaseAuth to create a User
        mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Users user = new Users(sUsername, sEmail);
                        MyDatabase.getDatabase().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }else{
                        Toast.makeText(RegisterActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Used to check the user's Internet connection
    public boolean checkConnection() {
        boolean isConnected;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            isConnected = true;
        }
        else
            isConnected = false;

        return isConnected;

    }



}