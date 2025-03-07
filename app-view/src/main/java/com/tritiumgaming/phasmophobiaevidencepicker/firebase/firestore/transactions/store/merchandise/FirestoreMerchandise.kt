package com.tritiumgaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise

import com.tritiumgaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.FirestoreMarketplace.Companion.storeCollection
import com.google.firebase.firestore.DocumentReference

class FirestoreMerchandise {

    companion object {
        private const val DOCUMENT_MERCHANDISE = "Merchandise"

        val merchandiseDocument: DocumentReference
            get() = storeCollection.document(DOCUMENT_MERCHANDISE)
    }
}
