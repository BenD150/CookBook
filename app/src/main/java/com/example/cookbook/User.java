package com.example.cookbook;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/*
    The user class that defines user object to CookBook App.
 */

public class User {

    // User information includes username, email address, saved recipe, and uploaded recipe.
    public String userName, email, UserID;
    private ArrayList<String> savedRecipe, uploadedRecipe;

    public User(){

    }

    // Constructor
    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
        savedRecipe = new ArrayList<String>();
        uploadedRecipe = new ArrayList<String>();
    }

    // Save recipe to current user's list
    public void saveRecipe(String recipeID){
        this.savedRecipe.add(recipeID);
    }

    // Upload action, create the uploadedrecipe object
    public void uploadRecipe(String dish, String ingredients, String directions, String description){
        UploadedRecipe recipe = new UploadedRecipe(dish, ingredients, directions, description);
        this.uploadedRecipe.add(recipe.getRcpID());
    }

    // This is the method that check if a certain recipe is created by THIS user.
    public boolean isCreator (@NonNull UploadedRecipe recipe){
        boolean indicator = false;
        if(this.uploadedRecipe.contains(recipe.getRcpID())){
            indicator = true;
        }
        return indicator;
    }


}
