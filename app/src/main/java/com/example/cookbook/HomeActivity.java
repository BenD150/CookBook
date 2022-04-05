package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DAOUser dao = new DAOUser();


        Log.i("HomeActivity", "onCreate has been called for HomeActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button settingsBtn = findViewById(R.id.settingsBtn);
        Button uploadFragment = findViewById(R.id.button_upload);
        Button savedFragment = findViewById(R.id.button_viewsaved);
        Button searchFragment = findViewById(R.id.button_search);
        TextView homeText = findViewById(R.id.homeText);
        Button logoutBtn = findViewById(R.id.logout);


        uploadFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Setting homeText and logout button to INVISIBLE so it doesn't show up in each fragment
                settingsBtn.setVisibility(View.INVISIBLE);
                homeText.setVisibility(View.INVISIBLE);
                logoutBtn.setVisibility(View.INVISIBLE);
                replaceFragment(new UploadFragment());
            }
        });

        savedFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Setting homeText and logout button to INVISIBLE so it doesn't show up in each fragment
                settingsBtn.setVisibility(View.INVISIBLE);
                homeText.setVisibility(View.INVISIBLE);
                logoutBtn.setVisibility(View.INVISIBLE);
                replaceFragment(new SavedFragment());
            }
        });

        searchFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Setting homeText and logout button to INVISIBLE so it doesn't show up in each fragment
                settingsBtn.setVisibility(View.INVISIBLE);
                homeText.setVisibility(View.INVISIBLE);
                logoutBtn.setVisibility(View.INVISIBLE);
                replaceFragment(new SearchFragment());
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}