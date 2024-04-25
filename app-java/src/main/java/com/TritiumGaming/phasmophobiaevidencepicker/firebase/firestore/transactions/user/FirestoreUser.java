package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class FirestoreUser {

    private static final String COLLECTION_USERS = "Users";

    @Nullable
    public static FirebaseUser getCurrentFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    public static DocumentReference buildUserDocument()
            throws Exception {

        DocumentReference currentUserDoc = getUserDocument();

        FirebaseUser user = getCurrentFirebaseUser();
        if(user == null) {
            throw new NullFirebaseUserException("Aborting creation of new User in database.");
        }

        currentUserDoc.set(new HashMap<String, Object>(), SetOptions.merge())
            .addOnSuccessListener(unused -> Log.d("Firestore", "User document " +
                    user.getUid() + " successfully FOUND!"))
            .addOnFailureListener(e -> {
                Log.d("Firestore", "User document " +
                        user.getUid() + " could NOT be GENERATED / LOCATED!");
                e.printStackTrace();
            })
            .addOnCompleteListener(task -> {
                Log.d("Firestore", "User document " +
                        user.getUid() + " process complete.");

                currentUserDoc.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        try {
                            FirestoreAccount.init();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.d("Firestore", "User document " +
                                user.getUid() + " successfully INITIALIZED!");
                    }
                });
            });

        return currentUserDoc;
    }

    @NonNull
    public static DocumentReference getUserDocument()
            throws Exception {

        if(getCurrentFirebaseUser() == null) {
            throw new NullFirebaseUserException();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference userCollection = db.collection(COLLECTION_USERS);
        return userCollection.document(getCurrentFirebaseUser().getUid());

    }

    public static String getCurrentFirebaseUserDisplayNameInitials(String displayName) {

        StringBuilder displayInitials = new StringBuilder();
        if (displayName != null) {
            String[] names = displayName.split(" ");

            for (String name : names) {
                String trimmedName = name.trim();
                if (!trimmedName.isEmpty()) {
                    char initial = trimmedName.charAt(0);
                    displayInitials.append(initial);
                    if (displayInitials.length() >= 2) {
                        break;
                    }
                }
            }
        }
        return displayInitials.toString();
    }

    public static class NullFirebaseUserException extends Exception {
        private static final String ERROR_MESSAGE = "FirebaseUser is null.";
        public NullFirebaseUserException() {
            super(ERROR_MESSAGE);
        }

        public NullFirebaseUserException(String extendedMessage) {
            super(ERROR_MESSAGE + " " + extendedMessage);
        }
    }

}
