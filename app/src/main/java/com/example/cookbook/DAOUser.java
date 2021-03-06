package com.example.cookbook;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOUser {

    private DatabaseReference databaseReference;

    public DAOUser(){
        FirebaseDatabase db = MyDatabase.getDatabase();
        databaseReference = db.getReference(Users.class.getSimpleName());
    }
    // Used to update a user's information
    public Task<Void> update(String key, HashMap<String, Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }
    // Remove a user from the database
    public Task<Void> delete(String key) {
        return databaseReference.child(key).removeValue();
    }
}
