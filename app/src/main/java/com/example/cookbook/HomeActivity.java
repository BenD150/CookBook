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


public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("HomeActivity", "onCreate has been called for HomeActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button logoutButton = findViewById(R.id.logoutBtn);
        Button uploadFragment = findViewById(R.id.button_upload);
        Button savedFragment = findViewById(R.id.button_viewsaved);
        TextView homeText = findViewById(R.id.homeText);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        uploadFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Setting homeText and logout button to INVISIBLE so it doesn't show up in each fragment
                logoutButton.setVisibility(View.INVISIBLE);
                homeText.setVisibility(View.INVISIBLE);
                replaceFragment(new UploadFragment());
            }
        });

        savedFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Setting homeText and logout button to INVISIBLE so it doesn't show up in each fragment
                logoutButton.setVisibility(View.INVISIBLE);
                homeText.setVisibility(View.INVISIBLE);
                replaceFragment(new SavedFragment());
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

/*
Use Cases:

User Account Management
    - Login, create or delete accounts, forgot password reset

Users
    - Create Recipe
        - User adds ingredients, instructions to make it, and an optional photo

    - Browse Recipes
        - User searches for a keyword
        - Firebase returns recipes with the keyword in it
        - User can save the recipe for future reference

    - View Saved Recipes
        - All created and saved recipes will show up and you can tap the one you want to make
        - Once the recipe is tapped on, the full recipe will show
        - Option to delete the saved recipe from the local account

    - Logout button
 */