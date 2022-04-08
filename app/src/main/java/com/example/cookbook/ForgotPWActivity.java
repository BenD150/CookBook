package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPWActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwactivity);

        Button submitBtn = findViewById(R.id.submitBtn2);
        EditText email = findViewById(R.id.userEmail);


        //on click listener for the forgot password activity
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email.getText().toString();

                //send the user an email to reset password
                auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPWActivity.this, "Forgot Password Email Sent!", Toast.LENGTH_SHORT);
                        }else{
                            Toast.makeText(ForgotPWActivity.this, "Email Failed to Send!", Toast.LENGTH_SHORT);
                        }
                    }
                });

                //send the user back to the login screen to try again after reset password
                startActivity(new Intent(ForgotPWActivity.this, LoginActivity.class));
            }
        });
    }
}