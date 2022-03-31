package com.example.cookbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;


public class SavedFragment extends Fragment implements RecyclerViewInterface{

    ArrayList<RecipeModel> recipeModels = new ArrayList<>();
    int[] recipeImages = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background
    , R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};
    Recipe_RecyclerViewAdapter adapter = new Recipe_RecyclerViewAdapter(this.getContext(), recipeModels, this);

    public SavedFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("SavedFragment", "onCreateView has been called for SavedFragment");
        // Inflate the layout for this fragment
        //view = inflater.inflate(R.layout.fragment_saved, container, false);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_saved, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.mRecyclerView);
        setUpRecipeModels();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        EditText nameSearch = view.findViewById(R.id.nameSearch);
        nameSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        return view;
    }

    private void filter(String text) {
        ArrayList<RecipeModel> filteredList = new ArrayList<>();

        for (RecipeModel recipe: recipeModels) {
            if (recipe.getRecipeName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(recipe);
            }
        }

        adapter.filterList(filteredList);
    }


    private void setUpRecipeModels() {
        // This is where we will pull the recipe data from Firebase. Right now, I have it hard coded
        // Keep in mind that this is missing instructions/steps
        String[] recipeNames = getResources().getStringArray(R.array.recipe_names);
        String[] prepTimes = getResources().getStringArray(R.array.prep_time);
        String[] instructionsAndSteps = getResources().getStringArray(R.array.IngredientsAndSteps);
        String[] cookTimes = getResources().getStringArray(R.array.cook_time);

        for (int i = 0; i < recipeNames.length; i++) {
            recipeModels.add(new RecipeModel((i+1), recipeNames[i],
                    "Prep Time: " + prepTimes[i],
                    "Cook Time: " + cookTimes[i],
                    instructionsAndSteps[i],
                    recipeImages[i]));
        }
    }


    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(view.getContext(), SingleRecipeActivity.class);

        intent.putExtra("RECIPENAME", recipeModels.get(position).getRecipeName());
        intent.putExtra("PREPTIME", recipeModels.get(position).getPrepTime());
        intent.putExtra("COOKTIME", recipeModels.get(position).getCookTime());
        intent.putExtra("INSTRANDSTEPS", recipeModels.get(position).getInstructionsAndSteps());
        intent.putExtra("IMAGE", recipeModels.get(position).getImage());

        startActivity(intent);
    }

    @Override
    public void onRecipeLongClick(int position) {
        recipeModels.remove(position);
        adapter.notifyItemRemoved(position);
    }
}