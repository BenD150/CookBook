package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set View and Buttons
        Log.i("MainActivity", "onCreate has been called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.login);
        Button registerButton = findViewById(R.id.register);

        Log.i("MainActivity", "Buttons have been found.");
        // Send user to Login screen
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        Log.i("LoginActivity", "Switched to LoginActivity");
        // Send user to Register screen
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        Log.i("RegisterActivity", "Switched to RegisterActivity");
    }
}

