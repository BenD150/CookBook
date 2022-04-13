package com.example.cookbook;

import android.content.Intent;
import android.net.Uri;
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

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.util.ArrayList;


public class SavedFragment extends Fragment implements RecyclerViewInterface{

    ArrayList<RecipeModel> recipeModels = new ArrayList<>();
    Recipe_RecyclerViewAdapter adapter = new Recipe_RecyclerViewAdapter(this.getContext(), recipeModels, this);
    String uid = "";

    public SavedFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("SavedFragment", "onCreateView has been called for SavedFragment");
        // Inflate the layout for this fragment
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_saved, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.mRecyclerView);
        setUpRecipeModels();
        System.out.println("Just before setting up adapter, recipe models length is " + recipeModels.size());
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
        // establish firebase connection and set a reference point to root node

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference savedRecipes = MyDatabase.getDatabase().getReference();

        // go down to RecipeModel child
        savedRecipes.child("Users").child(currentUserId).child("savedRecipes").addValueEventListener(new ValueEventListener() {
            //this method will be invoked anytime the database be changed
            @Override
            public void onDataChange(DataSnapshot snapshot) {
               Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child: children) {

                    // retrieve the recipe as a recipeModel object
                    RecipeModel temp = child.getValue(RecipeModel.class);
                    // add it to the arraylist for display
                    recipeModels.add(new RecipeModel(temp.getRecipeName(), temp.getPrepTime(),
                            temp.getCookTime(), temp.getInstructionsAndSteps(), temp.getImage(), temp.getCreator(), child.getKey()));
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
        Intent intent = new Intent(view.getContext(), SavedRecipeActivity.class);

        intent.putExtra("RECIPENAME", adapter.getItem(position).getRecipeName());
        intent.putExtra("PREPTIME", adapter.getItem(position).getPrepTime());
        intent.putExtra("COOKTIME", adapter.getItem(position).getCookTime());
        intent.putExtra("INGRANDSTEPS", adapter.getItem(position).getInstructionsAndSteps());
        intent.putExtra("IMAGE", adapter.getItem(position).getImage());
        intent.putExtra("CREATOR", adapter.getItem(position).getCreator());
        intent.putExtra("UID", adapter.getItem(position).getUid());

        startActivity(intent);
    }
}