package com.tritiumstudios.phasmophobiaevidencetool.firebase.firestore.transactions.store.microtransactions;

import androidx.annotation.NonNull;

import com.tritiumstudios.phasmophobiaevidencetool.firebase.firestore.transactions.store.FirestoreMarketplace;
import com.google.firebase.firestore.DocumentReference;

public class FirestoreMicrotransactions {

    private static final String
            DOCUMENT_MICROTRANSACTIONS = "Microtransactions";

    @NonNull
    public static DocumentReference getMicrotransactionsDocument() throws Exception {
        return FirestoreMarketplace.getStoreCollection()
                .document(DOCUMENT_MICROTRANSACTIONS);
    }

}
