package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Locale;

public class SavedRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipe);

        // Get tbe strings from the intent
        String recipeName = getIntent().getStringExtra("RECIPENAME");
        String prepTime = getIntent().getStringExtra("PREPTIME");
        String cookTime = getIntent().getStringExtra("COOKTIME");
        String instrAndSteps = getIntent().getStringExtra("INGRANDSTEPS");
        String recipeImage = getIntent().getStringExtra("IMAGE");
        String uid = getIntent().getStringExtra("UID");
        String creator = getIntent().getStringExtra("CREATOR");

        Button removeRecipe = findViewById(R.id.removeBtn);

        TextView recipeNameView = findViewById(R.id.removeRecipeName);
        TextView prepTimeView = findViewById(R.id.removePrepTime);
        TextView cookTimeView = findViewById(R.id.removeCookTime);
        TextView instrAndStepsView = findViewById(R.id.removeIngrAndSteps);
        TextView creatorView = findViewById(R.id.creator2);
        ImageView recipeImageView = findViewById(R.id.recipeImage2);

        recipeNameView.setText(recipeName);
        String displayLanguage = Locale.getDefault().getDisplayLanguage();
        String newPrep = "";
        String newCook = "";
        // Depends on the user's selected language
        String creatorHelperString = "Created by: ";
        if(displayLanguage.equals("??????")){
            newPrep = "????????????: " + prepTime + " ??????.";
            newCook = "????????????: " + cookTime + " ??????.";
            creatorHelperString = "?????????:";
        } else {
            newPrep = "Prep Time: " + prepTime + " min.";
            newCook = "Cook Time: " + cookTime + " min.";
        }
        prepTimeView.setText(newPrep);
        cookTimeView.setText(newCook);
        instrAndStepsView.setText(instrAndSteps);
        creatorView.setText(creatorHelperString + creator);
        Glide.with(getApplicationContext()).load(recipeImage).override(108, 108).into(recipeImageView);

        // Remove a recipe from the user's saved recipes
        removeRecipe.setOnClickListener(view -> {
            if (checkConnection() == false) {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_LONG).show();
            } else {
                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference savedRecipes = MyDatabase.getDatabase().getReference();

                // go down to RecipeModel child

                savedRecipes.child("Users").child(currentUserId).child("savedRecipes").child(uid).removeValue().addOnSuccessListener(success ->
                {
                    Toast.makeText(getApplicationContext(), "Recipe Removed Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }).addOnFailureListener(error ->
                {
                    Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                });
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