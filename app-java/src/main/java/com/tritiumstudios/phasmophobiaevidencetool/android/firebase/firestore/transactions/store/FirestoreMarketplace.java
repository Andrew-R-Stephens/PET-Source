package com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.transactions.store;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreMarketplace {

    private static final String
            COLLECTION_STORE = "Store";

    @NonNull
    public static CollectionReference getStoreCollection() throws Exception {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(COLLECTION_STORE);
    }

}
