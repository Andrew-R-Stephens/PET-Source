package com.tritiumgaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.microtransactions

import com.google.firebase.firestore.DocumentReference
import com.tritiumgaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.FirestoreMarketplace.Companion.storeCollection

class FirestoreMicrotransactions {
    companion object {
        private const val DOCUMENT_MICRO_TRANSACTIONS = "Microtransactions"

        val microTransactionsDocument: DocumentReference
            get() = storeCollection
                .document(DOCUMENT_MICRO_TRANSACTIONS)
    }

}
