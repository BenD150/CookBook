package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        DAOUser dao = new DAOUser();

        Button changePW = findViewById(R.id.changePW);
        Button deleteAcct = findViewById(R.id.deleteAcct);
        Button backBtn = findViewById(R.id.backBtn);



        changePW.setOnClickListener(view -> {
            if (checkConnection() == false) {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_LONG).show();
            } else {
                startActivity(new Intent(SettingsActivity.this, ChangeUserActivity.class));
            }
        });


        deleteAcct.setOnClickListener(view -> {
            if (checkConnection() == false) {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_LONG).show();
            } else {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String myUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                dao.delete(myUser).addOnSuccessListener(success ->
                {
                    Toast.makeText(SettingsActivity.this, "Account Successfully Deleted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(error ->
                {
                    Toast.makeText(SettingsActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

                AuthCredential credential = EmailAuthProvider.getCredential("user@example.com", "password1234");
                user.reauthenticate(credential).addOnCompleteListener(task -> {
                    //deletes the user when reauthentication
                    user.delete().addOnSuccessListener(success ->
                    {
                        Toast.makeText(SettingsActivity.this, "Account Successfully Deleted", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(error ->
                    {
                        Toast.makeText(SettingsActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                    startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                });
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
            }
        });

        backBtn.setOnClickListener(view -> startActivity(new Intent(SettingsActivity.this, HomeActivity.class)));
    }



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