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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SavedRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipe);

        String recipeName = getIntent().getStringExtra("RECIPENAME");
        String prepTime = getIntent().getStringExtra("PREPTIME");
        String cookTime = getIntent().getStringExtra("COOKTIME");
        String instrAndSteps = getIntent().getStringExtra("INGRANDSTEPS");
        int recipeImage = getIntent().getIntExtra("IMAGE", 0);
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
        String newPrep = prepTime + " min.";
        String newCook = cookTime + " min.";
        prepTimeView.setText(newPrep);
        cookTimeView.setText(newCook);
        instrAndStepsView.setText(instrAndSteps);
        creatorView.setText("Created by: " + creator);
        recipeImageView.setImageResource(recipeImage);


        removeRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference savedRecipes = FirebaseDatabase.getInstance().getReference();

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
}