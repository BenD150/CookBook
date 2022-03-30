package com.example.cookbook;

public class RecipeModel {
    String recipeName;
    String prepTime;
    String cookTime;
    //Will need instructions and steps later
    int image;

    public RecipeModel(String recipeName, String prepTime, String cookTime, int image) {
        this.recipeName = recipeName;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.image = image;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public String getCookTime() {
        return cookTime;
    }

    public int getImage() {
        return image;
    }
}
