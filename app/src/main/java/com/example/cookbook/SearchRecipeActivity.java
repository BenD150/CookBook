package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SearchRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        String recipeName = getIntent().getStringExtra("RECIPENAME");
        String prepTime = getIntent().getStringExtra("PREPTIME");
        String cookTime = getIntent().getStringExtra("COOKTIME");
        String ingrAndSteps = getIntent().getStringExtra("INGRANDSTEPS");
        int recipeImage = getIntent().getIntExtra("IMAGE", 0);
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
        String newPrep = "Prep Time: " + prepTime + " min.";
        String newCook = "Cook Time: " + cookTime + " min.";
        prepTimeView.setText(newPrep);
        cookTimeView.setText(newCook);
        instrAndStepsView.setText(ingrAndSteps);
        creatorView.setText("Created by: " + creator);
        recipeImageView.setImageResource(recipeImage);

        searchRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RecipeModel newRecipe = new RecipeModel(recipeName, prepTime, cookTime, ingrAndSteps, recipeImage, creator, uid);

                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("savedRecipes").push().setValue(newRecipe).addOnSuccessListener(success ->
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
}
