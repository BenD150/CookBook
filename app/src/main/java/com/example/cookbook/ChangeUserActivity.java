package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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


        String userEmail = currentEmail.getText().toString();
        String userPW = currentPW.getText().toString();
        String newUserPW = newPW.getText().toString();


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangeUserActivity.this, HomeActivity.class));
            }
        });



    }
}