package com.example.cookbook;

import com.google.firebase.database.FirebaseDatabase;

public class MyDatabase {

    private static FirebaseDatabase mDatabase;

    // Gets the instance of the database, and enables offline capabilities
    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}



