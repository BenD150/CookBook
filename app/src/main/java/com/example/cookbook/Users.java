package com.example.cookbook;


/*
    The user class that defines user object to CookBook App.
 */

public class Users {

    // User information includes username, email address, saved recipe, and uploaded recipe.
    public String userName, email;

    // Empty constructor used for Firebase
    public Users(){
    }

    // Constructor
    public Users(String userName, String email){
        this.userName = userName;
        this.email = email;
    }


}
