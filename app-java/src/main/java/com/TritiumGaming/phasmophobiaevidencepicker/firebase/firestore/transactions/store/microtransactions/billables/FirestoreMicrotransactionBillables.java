package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.microtransactions.billables;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.microtransactions.FirestoreMicrotransactions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreMicrotransactionBillables {

    private static final String COLLECTION_BILLABLES = "Billables";

    public static CollectionReference getBillablesCollection() throws Exception {
        return FirestoreMicrotransactions.getMicrotransactionsDocument()
                .collection(COLLECTION_BILLABLES);
    }


    public static Task<QuerySnapshot> getBillablesWhere(
            String filterField, String value, String orderField, Query.Direction order)
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
