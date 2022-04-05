package com.example.cookbook;

public class RecipeModel {
    String recipeName;
    String prepTime;
    String cookTime;
    //Will need instructions and steps later
    String instructionsAndSteps;
    String creator;
    String uid;
    String image;



    // Empty constructor used for Firebase
    public RecipeModel(){}

    public RecipeModel(String recipeName, String prepTime, String cookTime, String instructionsAndSteps, String image, String creator, String uid) {
        this.recipeName = recipeName;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.instructionsAndSteps = instructionsAndSteps;
        this.image = image;
        this.creator = creator;
        this.uid = uid;
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
    public String getImage() {
        return image;
    }
    public String getCreator() {
        return creator;
    }
    public String getUid() {
        return uid;
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
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public void setImage(String image) {
        this.image = image;
    }

}
