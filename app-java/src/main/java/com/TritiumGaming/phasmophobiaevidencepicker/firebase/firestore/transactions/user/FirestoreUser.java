package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreTransactionHistory;
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
