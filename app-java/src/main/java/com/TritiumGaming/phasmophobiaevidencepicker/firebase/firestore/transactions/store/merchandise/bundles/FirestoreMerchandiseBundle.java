package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.bundles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.FirestoreMerchandise;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreMerchandiseBundle {

    private static final String COLLECTION_BUNDLES = "Bundles";

    @NonNull
    public static CollectionReference getBundlesCollection() {
        return FirestoreMerchandise.getMerchandiseDocument().collection(COLLECTION_BUNDLES);
    }

    @NonNull
    public static Task<QuerySnapshot> getBundleWhere(
            @Nullable String filterField,
            @Nullable String value,
            @Nullable String orderField,
            @Nullable Query.Direction order) {

        Query query = FirestoreMerchandiseBundle.getBundlesCollection();
        if(filterField != null && value != null) {
            query = FirestoreMerchandiseBundle.getBundlesCollection()
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
