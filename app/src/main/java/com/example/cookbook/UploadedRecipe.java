package com.example.cookbook;

import java.util.ArrayList;

public class UploadedRecipe {
    private String dish, ingredients, directions, description, rcpID, madeBy;
    private double rating;

    // Constructor
    public UploadedRecipe(String dish, String ingredients, String directions, String description){
        this.dish = dish;
        this.ingredients = ingredients;
        this.directions = directions;
        this.description = description;
        this.rating = 0.0;
    }

    public String getRcpID(){
        return this.rcpID;
    }
}
