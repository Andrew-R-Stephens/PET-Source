package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.microtransactions.billables;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.microtransactions.FirestoreMicrotransactions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreMicrotransactionBillables {

    private static final String COLLECTION_BILLABLES = "Billables";

    @NonNull
    public static CollectionReference getBillablesCollection() throws Exception {
        return FirestoreMicrotransactions.getMicrotransactionsDocument()
                .collection(COLLECTION_BILLABLES);
    }


    @NonNull
    public static Task<QuerySnapshot> getBillablesWhere(
            @NonNull String filterField, String value, @Nullable String orderField, @Nullable Query.Direction order)
            throws Exception {

        Query query = FirestoreMicrotransactionBillables.getBillablesCollection()
                .whereEqualTo(filterField, value);

        if(orderField == null || order == null) {
            return query.get();
        }

        return query
                .orderBy(orderField, order)
                .get();
    }

}
