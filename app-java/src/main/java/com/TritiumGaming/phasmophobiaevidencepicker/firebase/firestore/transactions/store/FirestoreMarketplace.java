package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store;

import android.util.Log;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreAccount;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreMarketplace {

    private static final String
            COLLECTION_STORE = "Store";

    @NonNull
    public static CollectionReference getStoreCollection() throws Exception {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(COLLECTION_STORE);
    }

}
