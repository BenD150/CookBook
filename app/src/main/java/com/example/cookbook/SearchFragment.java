package com.example.cookbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class SearchFragment extends Fragment implements RecyclerViewInterface {

    ArrayList<RecipeModel> recipeModels = new ArrayList<>();
    int[] recipeImages = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background
            , R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};
    Recipe_RecyclerViewAdapter adapter = new Recipe_RecyclerViewAdapter(this.getContext(), recipeModels, this);
    String uid = "";

    public SearchFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.mRecyclerView2);
        setUpRecipeModels();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        EditText nameSearch = view.findViewById(R.id.nameSearch2);
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
            if (recipe.getRecipeName().toLowerCase().contains(text.toLowerCase())
                    || recipe.getInstructionsAndSteps().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(recipe);
            }
        }

        adapter.filterList(filteredList);
    }


    private void setUpRecipeModels() {
        // establish firebase connection and set a reference point to root node

        DatabaseReference searchRecipes = FirebaseDatabase.getInstance().getReference();

        // go down to RecipeModel child. The pulling of data is slightly different from Saved Recipes
        searchRecipes.child("RecipeModel").addValueEventListener(new ValueEventListener() {
            //this method will be invoked anytime the database be changed
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child: children) {

                    // retrieve the recipe as a recipeModel object
                    RecipeModel temp = child.getValue(RecipeModel.class);
                    System.out.println("The key is " + child.getKey());

                    // add it to the arraylist for display
                    recipeModels.add(new RecipeModel(temp.getRecipeName(), "Prep Time: " + temp.getPrepTime(),
                            "Cook Time: " + temp.getCookTime(), temp.getInstructionsAndSteps(), recipeImages[0], child.getKey(), child.getKey()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println(error.getMessage());
            }
        });

    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(view.getContext(), SingleRecipeActivity.class);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RecipeModel");
        intent.putExtra("RECIPENAME", recipeModels.get(position).getRecipeName());
        intent.putExtra("PREPTIME", recipeModels.get(position).getPrepTime());
        intent.putExtra("COOKTIME", recipeModels.get(position).getCookTime());
        intent.putExtra("INSTRANDSTEPS", recipeModels.get(position).getInstructionsAndSteps());
        intent.putExtra("IMAGE", recipeModels.get(position).getImage());
        intent.putExtra("CREATOR", recipeModels.get(position).getCreator());
        intent.putExtra("UID", recipeModels.get(position).getUid());

        startActivity(intent);
    }


}
