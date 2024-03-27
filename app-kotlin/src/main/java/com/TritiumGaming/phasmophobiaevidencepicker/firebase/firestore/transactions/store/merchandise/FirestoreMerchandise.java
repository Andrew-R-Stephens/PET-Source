package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.FirestoreMarketplace;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

public class FirestoreMerchandise {

    private static final String
            DOCUMENT_MERCHANDISE = "Merchandise";

    @NonNull
    public static DocumentReference getMerchandiseDocument() throws Exception {
        return FirestoreMarketplace.getStoreCollection().document(DOCUMENT_MERCHANDISE);
    }

}
