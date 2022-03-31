package com.example.cookbook;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAORecipeModel {

    private DatabaseReference databaseReference;

    public DAORecipeModel() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(RecipeModel.class.getSimpleName());
    }

    public Task<Void> add(RecipeModel recipeModel) {
        return databaseReference.push().setValue(recipeModel);
    }
}
