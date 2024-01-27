package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreMarketplace {

    private static final String
            COLLECTION_STORE = "Store",
            DOCUMENT_MERCHANDISE = "Merchandise",

            COLLECTION_THEMES = "Themes",
            COLLECTION_BUNDLES = "Bundles";

    public static CollectionReference getThemeCollection() throws Exception {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("Firestore", "READING Store collection.");
        CollectionReference storeCollection = db.collection(COLLECTION_STORE);
        Log.d("Firestore", "READING Merchandise document.");
        DocumentReference merchandiseDocument = storeCollection.document(DOCUMENT_MERCHANDISE);

        Log.d("Firestore", "READING Themes collection.");

        return merchandiseDocument.collection(COLLECTION_THEMES);
    }

    public static CollectionReference getBundlesCollection() throws Exception {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("Firestore", "READING Store collection.");
        CollectionReference storeCollection = db.collection(COLLECTION_STORE);
        Log.d("Firestore", "READING Merchandise document.");
        DocumentReference merchandiseDocument = storeCollection.document(DOCUMENT_MERCHANDISE);

        Log.d("Firestore", "READING Themes collection.");

        return merchandiseDocument.collection(COLLECTION_BUNDLES);
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

    public static Task<QuerySnapshot> getBundleWhere(
            String filterField, String value, String orderField, Query.Direction order)
            throws Exception {

        Query query = FirestoreMarketplace.getBundlesCollection();
        if(filterField != null && value != null) {
            query = FirestoreMarketplace.getBundlesCollection()
                    .whereEqualTo(filterField, value);
        }

        if(orderField != null && order != null) {
            return query
                    .orderBy(orderField, order)
                    .get();
        }

        return query.get();
    }


}
