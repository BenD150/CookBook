package com.example.cookbook;

public class RecipeModel {
    String recipeName;
    String prepTime;
    String cookTime;
    //Will need instructions and steps later
    String instructionsAndSteps;
    int image;

    // Empty constructor used for Firebase
    public RecipeModel(){}

    public RecipeModel(String recipeName, String prepTime, String cookTime, String instructionsAndSteps, int image) {
        this.recipeName = recipeName;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.instructionsAndSteps = instructionsAndSteps;
        this.image = image;
    }

    // Getters
    public String getRecipeName() {
        return recipeName;
    }
    public String getPrepTime() {
        return prepTime;
    }
    public String getInstructionsAndSteps() { return instructionsAndSteps; }
    public String getCookTime() {
        return cookTime;
    }
    public int getImage() {
        return image;
    }


    // Setters
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }
    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }
    public void setInstructionsAndSteps(String instructionsAndSteps) {
        this.instructionsAndSteps = instructionsAndSteps;
    }
    public void setImage(int image) {
        this.image = image;
    }

}
