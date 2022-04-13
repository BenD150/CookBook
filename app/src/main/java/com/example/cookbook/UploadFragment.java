package com.example.cookbook;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class UploadFragment extends Fragment {

    SharedPreferences sharedpreference;
    int highestID = 0;
    String creator;
    private ImageView foodImage;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String fileName = "";


    public UploadFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("UploadFragment", "onCreate has been called for UploadFragment");
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        //Find the current user
        FirebaseDatabase database = MyDatabase.getDatabase();
        DatabaseReference dbReference = database.getReference();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //gets the current users info
        dbReference.child("Users").child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //establishes the creator of the recipe to be uploaded
                creator = snapshot.child("userName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[] {
                            Manifest.permission.CAMERA
                    }, 100);
        }

        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 100);
        }

        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 100);
        }


        addPhoto.setOnClickListener(view1 -> {

            // Help from Joel's comment: https://stackoverflow.com/questions/21388586/get-uri-from-camera-intent-in-android
            // On physical Android device, must ask for permission to read and write from external storage

            String fileName = UUID.randomUUID().toString();;
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put(MediaStore.Images.Media.DESCRIPTION, "Image captured by camera");
            imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, 1);

            //choosePicture();
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
        foodImage = getActivity().findViewById(R.id.foodImage);

        uploadRecipe.setOnClickListener(view12 -> {
            String totalFile = "https://firebasestorage.googleapis.com/v0/b/cookbook-app-834e9.appspot.com/o/images%2F" + fileName + "?alt=media&token=";
            RecipeModel newRecipe = new RecipeModel(recipeName.getText().toString(), prepTime.getText().toString(), cookTime.getText().toString(), instrAndSteps.getText().toString(), totalFile, creator, "");
            dao.add(newRecipe).addOnSuccessListener(success ->
            {
                Toast.makeText(getActivity(), "Recipe Uploaded Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }).addOnFailureListener(error ->
            {
                Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            });

            // Get user's email address and add uploaded recipe to them

            //adds the recipe to the users saved recipes
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            MyDatabase.getDatabase().getReference().child("Users").child(currentUserId).child("savedRecipes").push().setValue(newRecipe);




        });
    }

    private void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // Help from Joel's comment: https://stackoverflow.com/questions/21388586/get-uri-from-camera-intent-in-android

        super.onActivityResult(requestCode, resultCode, data);


        ImageView foodImage = getActivity().findViewById(R.id.foodImage);
        if (requestCode == 1) {
            // Get Image
            ContentResolver cr = getActivity().getContentResolver();
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(cr, imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Give it to the ImageView
            foodImage.setImageBitmap(photo);
            uploadPicture();
        }
    }

        /*
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            foodImage.setImageURI(imageUri);
            uploadPicture();
        }
    }

         */


    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Uploading Picture...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        fileName = randomKey;
        StorageReference riversRef = storageReference.child("images/" + randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    pd.dismiss();
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), "Failed to Upload.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(snapshot -> {
                    double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    pd.setMessage("Percentage: " + (int)progressPercent + "%");
                });
    }

    public void onDestroyView() {
        super.onDestroyView();
    }
}