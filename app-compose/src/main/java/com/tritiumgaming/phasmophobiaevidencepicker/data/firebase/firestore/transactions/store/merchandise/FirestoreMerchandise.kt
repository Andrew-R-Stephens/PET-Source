package com.tritiumgaming.phasmophobiaevidencepicker.data.firebase.firestore.transactions.store.merchandise

import com.google.firebase.firestore.DocumentReference
import com.tritiumgaming.phasmophobiaevidencepicker.data.firebase.firestore.transactions.store.FirestoreMarketplace.Companion.storeCollection

class FirestoreMerchandise {

    companion object {
        private const val DOCUMENT_MERCHANDISE = "Merchandise"

        val merchandiseDocument: DocumentReference
            get() = storeCollection.document(DOCUMENT_MERCHANDISE)
    }
}
