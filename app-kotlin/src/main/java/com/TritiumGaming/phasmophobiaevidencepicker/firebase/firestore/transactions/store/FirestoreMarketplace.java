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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirestoreMarketplace {

    private static final String
            COLLECTION_STORE = "Store",
            DOCUMENT_MERCHANDISE = "Merchandise",

            COLLECTION_THEMES = "Themes";

    public static CollectionReference getThemeCollection() throws Exception {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("Firestore", "READING Store collection.");
        CollectionReference storeCollection = db.collection(COLLECTION_STORE);
        Log.d("Firestore", "READING Merchandise document.");
        DocumentReference merchandiseDocument = storeCollection.document(DOCUMENT_MERCHANDISE);

        Log.d("Firestore", "READING Themes collection.");
        return merchandiseDocument.collection(COLLECTION_THEMES);
    }

    public static Task<QuerySnapshot> getThemes() throws Exception {
        return getThemeCollection().get();
    }

    public static Task<QuerySnapshot> getThemesWhere(String field, String value) throws Exception {
        return FirestoreMarketplace.getThemeCollection()
                .whereEqualTo(field, value)
                .get();
    }

    public static Task<QuerySnapshot> getThemesWhere(
            String filterField, String value, String orderField, Query.Direction order)
            throws Exception {

        Query query = FirestoreMarketplace.getThemeCollection()
                .whereEqualTo(filterField, value);

        if(orderField == null || order == null) {
            return query.get();
        }

        return query
                .orderBy(orderField, order)
                .get();
    }


}
