package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user;

import android.util.Log;

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

    public static FirebaseUser getCurrentFirebaseUser()
            throws Exception {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static DocumentReference buildUserDocument()
            throws Exception {

        DocumentReference currentUserDoc = getUserDocument();

        FirebaseUser user = getCurrentFirebaseUser();

        currentUserDoc.set(new HashMap<String, Object>(), SetOptions.merge())
            .addOnSuccessListener(unused -> Log.d("Firestore", "User document " + user.getUid() + " successfully FOUND!"))
            .addOnFailureListener(e -> {
                Log.d("Firestore", "User document " + user.getUid() + " could NOT be GENERATED / LOCATED!");
                e.printStackTrace();
            })
            .addOnCompleteListener(task -> {
                Log.d("Firestore", "User document " + user.getUid() + " process complete.");

                currentUserDoc.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        try {
                            FirestoreAccount.init();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        Log.d("Firestore", "User document " + user.getUid() + " successfully INITIALIZED!");
                    }
                });
            });

        return currentUserDoc;
    }

    public static DocumentReference getUserDocument()
            throws Exception {
        if(getCurrentFirebaseUser() == null) {
            throw new Exception("Null Pointer Exception: Firebase User not assigned");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference userCollection = db.collection(COLLECTION_USERS);
        return userCollection.document(getCurrentFirebaseUser().getUid());

    }

}
