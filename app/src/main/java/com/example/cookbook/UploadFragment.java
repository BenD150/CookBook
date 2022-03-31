package com.example.cookbook;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UploadFragment extends Fragment {

    SharedPreferences sharedpreference;

    public UploadFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("UploadFragment", "onCreate has been called for UploadFragment");
        super.onCreate(savedInstanceState);
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("UploadFragment", "onCreateView has been called for UploadFragment");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upload, container, false);
        sharedpreference= getActivity().getApplicationContext().getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
        return view;
    }

    public void onViewCreated (View view, Bundle savedInstanceState) {

        DAORecipeModel dao = new DAORecipeModel();

        // Open the camera if the add image button is clicked
        Button addPhoto = getActivity().findViewById(R.id.addPhoto);

        // Used this video to help: https://www.youtube.com/watch?v=RaOyw84625w

        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[] {
                            Manifest.permission.CAMERA
                    }, 100);
        }

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });


        // Return back to the homepage if the Cancel button is clicked
        Button cancelCreation = getActivity().findViewById(R.id.cancelCreation);
        cancelCreation.setOnClickListener(view2 -> {
            Intent intent = new Intent(this.getActivity(), HomeActivity.class);
            startActivity(intent);
        });

        // Upload recipe to database
        Button uploadRecipe = getActivity().findViewById(R.id.createRecipe);
        EditText recipeName = getActivity().findViewById(R.id.recipeName);
        EditText prepTime = getActivity().findViewById(R.id.prepTime);
        EditText cookTime = getActivity().findViewById(R.id.cookTime);
        EditText instrAndSteps = getActivity().findViewById(R.id.instructions);
        ImageView foodImage = getActivity().findViewById(R.id.foodImage);
        uploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeModel newRecipe = new RecipeModel(1, recipeName.getText().toString(), prepTime.getText().toString(), cookTime.getText().toString(), instrAndSteps.getText().toString(), foodImage.getId());
                dao.add(newRecipe).addOnSuccessListener(success ->
                {
                    Toast.makeText(getActivity(), "Recipe Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(error ->
                {
                    Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

                // Get user's email address and add uploaded recipe to them

                String userEmail = sharedpreference.getString("user_email", "");
                System.out.println("User Email is " + userEmail);



            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView foodImage = getActivity().findViewById(R.id.foodImage);
        if (requestCode == 100) {
            // Get Image
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Give it to the ImageView
            foodImage.setImageBitmap(photo);
        }
    }







}