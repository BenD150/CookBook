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

public class SingleRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        String recipeName = getIntent().getStringExtra("RECIPENAME");
        String prepTime = getIntent().getStringExtra("PREPTIME");
        String cookTime = getIntent().getStringExtra("COOKTIME");
        String instrAndSteps = getIntent().getStringExtra("INSTRANDSTEPS");
        int recipeImage = getIntent().getIntExtra("IMAGE", 0);
        String uid = getIntent().getStringExtra("UID");
        System.out.println("Our UID is " + uid);

        Button removeRecipe = findViewById(R.id.removeBtn);

        TextView recipeNameView = findViewById(R.id.recipeName2);
        TextView prepTimeView = findViewById(R.id.prepTime2);
        TextView cookTimeView = findViewById(R.id.cookTime2);
        TextView instrAndStepsView = findViewById(R.id.instrAndSteps);
        ImageView recipeImageView = findViewById(R.id.recipeImage2);


        DAORecipeModel dao = new DAORecipeModel();

        recipeNameView.setText(recipeName);
        prepTimeView.setText(prepTime);
        cookTimeView.setText(cookTime);
        instrAndStepsView.setText(instrAndSteps);
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