package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.view.Change;

import java.nio.charset.StandardCharsets;

public class ChangeUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);


        DAOUser dao = new DAOUser();

        EditText currentEmail = findViewById(R.id.currentEmail);
        EditText currentPW = findViewById(R.id.currentPW);
        EditText newPW = findViewById(R.id.newPW);

        Button cancelBtn = findViewById(R.id.cancelBtn);
        Button submitBtn = findViewById(R.id.submitBtn);

        cancelBtn.setOnClickListener(view -> startActivity(new Intent(ChangeUserActivity.this, SettingsActivity.class)));


        submitBtn.setOnClickListener(view -> {
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

            startActivity(new Intent(ChangeUserActivity.this, HomeActivity.class));
        });



    }
}