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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedFragment extends Fragment {

    ArrayList<RecipeModel> recipeModels = new ArrayList<>();
    int[] recipeImages = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background
    , R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SavedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedFragment newInstance(String param1, String param2) {
        SavedFragment fragment = new SavedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        RecyclerView recyclerView = getActivity().findViewById(R.id.mRecyclerView);
        setUpRecipeModels();
        Recipe_RecyclerViewAdapter adapter = new Recipe_RecyclerViewAdapter(getContext(), recipeModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("SavedFragment", "onCreateView has been called for SavedFragment");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_saved, container, false);
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