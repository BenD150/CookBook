package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreference;
    private EditText editTextEmail, editTextPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set View, Buttons, and Authorization
        Log.i("LoginActivity", "onCreate has been called for LoginActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signIn = findViewById(R.id.login2);
        Button forgotPW = findViewById(R.id.forgotPWBtn);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextTextEmailAddress2);
        editTextPass = findViewById(R.id.editTextTextPassword2);
        signIn.setOnClickListener(this);
        forgotPW.setOnClickListener(this);
        sharedPreference = getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v){

        // Log the user in
        if (v.getId() == R.id.login2) {
            if (checkConnection() == false) {
                Toast.makeText(LoginActivity.this , "No Internet Connection!", Toast.LENGTH_SHORT).show();
            }
            else {
                userLogin();
            }
        }

        // Let the user reset their password
        if (v.getId() == R.id.forgotPWBtn) {
            if (checkConnection() == false) {
                Toast.makeText(LoginActivity.this , "No Internet Connection!", Toast.LENGTH_SHORT).show();
            }
            else {
                startActivity(new Intent(LoginActivity.this, ForgotPWActivity.class));
            }
        }


    }

    // Log the user in
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Provide a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            editTextPass.setError(("Password is required!"));
            editTextPass.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                SharedPreferences.Editor editor = sharedPreference.edit();
                editor.putString("user_email", email);
                editor.commit();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            } else{
                Toast.makeText(LoginActivity.this , "Failed to Login!  Please make sure your credentials are correct or Register!", Toast.LENGTH_LONG).show();
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