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

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangeUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the View and get the Buttons and text fields that we need
        setContentView(R.layout.activity_change_user);

        EditText currentEmail = findViewById(R.id.currentEmail);
        EditText currentPW = findViewById(R.id.currentPW);
        EditText newPW = findViewById(R.id.newPW);

        Button cancelBtn = findViewById(R.id.cancelBtn);
        Button submitBtn = findViewById(R.id.submitBtn);

        // Go back to settings page if user clicks the Cancel button
        cancelBtn.setOnClickListener(view -> startActivity(new Intent(ChangeUserActivity.this, SettingsActivity.class)));

        // If the user has an Internet connection, change their password through FirebaseAuth
        submitBtn.setOnClickListener(view -> {
            if (checkConnection() == false) {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_LONG).show();
            } else {
                String userEmail = currentEmail.getText().toString();
                String userPW = currentPW.getText().toString();
                String newUserPW = newPW.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential(userEmail, userPW);
                user.reauthenticate(credential).addOnCompleteListener(task -> user.updatePassword(newUserPW).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(ChangeUserActivity.this, "Password has been changed successfully!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ChangeUserActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(ChangeUserActivity.this, "Failed to change Password.  Please make sure you old password matches the original! Try Again!", Toast.LENGTH_LONG).show();
                    }
                }));
                // Send user back to homepage
                startActivity(new Intent(ChangeUserActivity.this, HomeActivity.class));
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