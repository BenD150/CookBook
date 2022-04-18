package com.example.cookbook;

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

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set view, Buttons, and TextViews
        Log.i("HomeActivity", "onCreate has been called for HomeActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button settingsBtn = findViewById(R.id.settingsBtn);
        Button uploadFragment = findViewById(R.id.button_upload);
        Button savedFragment = findViewById(R.id.button_viewsaved);
        Button searchFragment = findViewById(R.id.button_search);
        TextView homeText = findViewById(R.id.homeText);
        Button logoutBtn = findViewById(R.id.logout);

        // Setting homeText, logout and settings button to INVISIBLE so it doesn't show up in each fragment

        uploadFragment.setOnClickListener(view -> {
            settingsBtn.setVisibility(View.INVISIBLE);
            homeText.setVisibility(View.INVISIBLE);
            logoutBtn.setVisibility(View.INVISIBLE);
            replaceFragment(new UploadFragment());
        });

        savedFragment.setOnClickListener(view -> {
            settingsBtn.setVisibility(View.INVISIBLE);
            homeText.setVisibility(View.INVISIBLE);
            logoutBtn.setVisibility(View.INVISIBLE);
            replaceFragment(new SavedFragment());
        });

        searchFragment.setOnClickListener(view -> {
            settingsBtn.setVisibility(View.INVISIBLE);
            homeText.setVisibility(View.INVISIBLE);
            logoutBtn.setVisibility(View.INVISIBLE);
            replaceFragment(new SearchFragment());
        });

        // Send user to the Settings activity
        settingsBtn.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, SettingsActivity.class)));

        // Log the user out
        logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}