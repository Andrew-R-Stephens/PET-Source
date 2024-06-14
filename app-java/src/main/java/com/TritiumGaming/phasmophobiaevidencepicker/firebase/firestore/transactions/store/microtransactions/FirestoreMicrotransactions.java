package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.microtransactions;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.FirestoreMarketplace;
import com.google.firebase.firestore.DocumentReference;

public class FirestoreMicrotransactions {

    private static final String
            DOCUMENT_MICROTRANSACTIONS = "Microtransactions";

    @NonNull
    public static DocumentReference getMicroTransactionsDocument() {
        return FirestoreMarketplace.getStoreCollection()
                .document(DOCUMENT_MICROTRANSACTIONS);
    }

}
