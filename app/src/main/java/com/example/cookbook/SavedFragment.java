package com.example.cookbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class SavedFragment extends Fragment {

    ArrayList<RecipeModel> recipeModels = new ArrayList<>();
    int[] recipeImages = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background
    , R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};


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
        recyclerView.setAdapter(new Recipe_RecyclerViewAdapter(this.getContext(), recipeModels));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }


    private void setUpRecipeModels() {
        // This is where we will pull the recipe data from Firebase. Right now, I have it hard coded
        String[] recipeNames = getResources().getStringArray(R.array.recipe_names);
        String[] prepTimes = getResources().getStringArray(R.array.prep_time);
        String[] cookTimes = getResources().getStringArray(R.array.cook_time);

        for (int i = 0; i < recipeNames.length; i++) {
            recipeModels.add(new RecipeModel(recipeNames[i],
                    prepTimes[i],
                    cookTimes[i],
                    recipeImages[i]));
        }
    }



}