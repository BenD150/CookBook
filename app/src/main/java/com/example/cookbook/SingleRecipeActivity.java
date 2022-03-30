package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_recipe);

        String recipeName = getIntent().getStringExtra("RECIPENAME");
        String prepTime = getIntent().getStringExtra("PREPTIME");
        String cookTime = getIntent().getStringExtra("COOKTIME");
        String instrAndSteps = getIntent().getStringExtra("INSTRANDSTEPS");
        int recipeImage = getIntent().getIntExtra("IMAGE", 0);

        TextView recipeNameView = findViewById(R.id.recipeName2);
        TextView prepTimeView = findViewById(R.id.prepTime2);
        TextView cookTimeView = findViewById(R.id.cookTime2);
        TextView instrAndStepsView = findViewById(R.id.instrAndSteps);
        ImageView recipeImageView = findViewById(R.id.recipeImage2);

        recipeNameView.setText(recipeName);
        prepTimeView.setText(prepTime);
        cookTimeView.setText(cookTime);
        instrAndStepsView.setText(instrAndSteps);
        recipeImageView.setImageResource(recipeImage);

    }
}