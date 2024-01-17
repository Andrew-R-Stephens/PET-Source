package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store;

import android.util.Log;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.theme.PETTheme;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirestoreMarketplace {

    private static final String
            COLLECTION_STORE = "Store",
            DOCUMENT_MERCHANDISE = "Merchandise",

            COLLECTION_THEMES = "Themes";

    public static CollectionReference getThemeCollection() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("Firestore", "READING Store collection.");
        CollectionReference storeCollection = db.collection(COLLECTION_STORE);
        Log.d("Firestore", "READING Merchandise document.");
        DocumentReference merchandiseDocument = storeCollection.document(DOCUMENT_MERCHANDISE);

        Log.d("Firestore", "READING Themes collection.");
        return merchandiseDocument.collection(COLLECTION_THEMES);
    }

    public static Task<QuerySnapshot> getThemes() {

        return getThemeCollection().get();
    }

}
