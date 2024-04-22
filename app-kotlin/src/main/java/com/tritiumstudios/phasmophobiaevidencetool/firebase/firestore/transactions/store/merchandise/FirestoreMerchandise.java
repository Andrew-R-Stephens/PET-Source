package com.tritiumstudios.phasmophobiaevidencetool.firebase.firestore.transactions.store.merchandise;

import androidx.annotation.NonNull;

import com.tritiumstudios.phasmophobiaevidencetool.firebase.firestore.transactions.store.FirestoreMarketplace;
import com.google.firebase.firestore.DocumentReference;

public class FirestoreMerchandise {

    private static final String
            DOCUMENT_MERCHANDISE = "Merchandise";

    @NonNull
    public static DocumentReference getMerchandiseDocument() throws Exception {
        return FirestoreMarketplace.getStoreCollection().document(DOCUMENT_MERCHANDISE);
    }

}
