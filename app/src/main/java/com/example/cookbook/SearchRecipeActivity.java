package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SearchRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        String recipeName = getIntent().getStringExtra("RECIPENAME");
        String prepTime = getIntent().getStringExtra("PREPTIME");
        String cookTime = getIntent().getStringExtra("COOKTIME");
        String ingrAndSteps = getIntent().getStringExtra("INGRANDSTEPS");
        String recipeImage = getIntent().getStringExtra("IMAGE");
        String uid = getIntent().getStringExtra("UID");
        String creator = getIntent().getStringExtra("CREATOR");

        Button searchRecipe = findViewById(R.id.saveBtnNew);

        TextView recipeNameView = findViewById(R.id.searchRecipeName);
        TextView prepTimeView = findViewById(R.id.searchPrepTime);
        TextView cookTimeView = findViewById(R.id.searchCookTime);
        TextView instrAndStepsView = findViewById(R.id.searchIngrAndSteps);
        TextView creatorView = findViewById(R.id.creator);
        ImageView recipeImageView = findViewById(R.id.recipeImage3);

        recipeNameView.setText(recipeName);
        String displayLanguage = Locale.getDefault().getDisplayLanguage();
        String newPrep = "";
        String newCook = "";
        String creatorHelperString = "Created by: ";
        if(displayLanguage.equals("中文")){
            newPrep = "准备时间: " + prepTime + " 分钟.";
            newCook = "烹饪时间: " + cookTime + " 分钟.";
            creatorHelperString = "作者为:";
        } else {
            newPrep = "Prep Time: " + prepTime + " min.";
            newCook = "Cook Time: " + cookTime + " min.";
        }
        prepTimeView.setText(newPrep);
        cookTimeView.setText(newCook);
        instrAndStepsView.setText(ingrAndSteps);
        creatorView.setText(creatorHelperString + creator);
        Glide.with(getApplicationContext()).load(recipeImage).override(108, 108).into(recipeImageView);

        searchRecipe.setOnClickListener(view -> {

            if (checkConnection() == false) {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_LONG).show();
            } else {
                RecipeModel newRecipe = new RecipeModel(recipeName, prepTime, cookTime, ingrAndSteps, recipeImage, creator, uid);

                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                MyDatabase.getDatabase().getReference().child("Users").child(currentUserId).child("savedRecipes").push().setValue(newRecipe).addOnSuccessListener(success ->
                {
                    Toast.makeText(getApplicationContext(), "Recipe Saved Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }).addOnFailureListener(error ->
                {
                    Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
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
