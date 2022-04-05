package com.example.cookbook;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/*
    The user class that defines user object to CookBook App.
 */

public class Users {

    // User information includes username, email address, saved recipe, and uploaded recipe.
    public String userName, email, UserID;
    private ArrayList<String> savedRecipe, uploadedRecipe;

    public Users(){

    }

    // Constructor
    public Users(String userName, String email){
        this.userName = userName;
        this.email = email;
        savedRecipe = new ArrayList<String>();
        uploadedRecipe = new ArrayList<String>();
    }


}
