package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.microtransactions;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.FirestoreMarketplace;
import com.google.firebase.firestore.DocumentReference;

public class FirestoreMicrotransactions {

    private static final String
            DOCUMENT_MICROTRANSACTIONS = "Microtransactions";

    public static DocumentReference getMicrotransactionsDocument() throws Exception {
        return FirestoreMarketplace.getStoreCollection()
                .document(DOCUMENT_MICROTRANSACTIONS);
    }

}
