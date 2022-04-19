package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPWActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwactivity);

        Button submitBtn = findViewById(R.id.submitBtn2);
        EditText email = findViewById(R.id.userEmail);


        submitBtn.setOnClickListener(view -> {

            if (checkConnection() == false) {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_LONG).show();
            } else {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email.getText().toString();

                // Send the user an email to reset password
                auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPWActivity.this, "Forgot Password Email Sent!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPWActivity.this, "Email Failed to Send!", Toast.LENGTH_SHORT).show();
                    }
                });

                // Send the user back to the login screen after resetting their password
                startActivity(new Intent(ForgotPWActivity.this, LoginActivity.class));
            }
        });
    }

    // Used to check the user's Internet connection
    public boolean checkConnection() {
        boolean isConnected = false;
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