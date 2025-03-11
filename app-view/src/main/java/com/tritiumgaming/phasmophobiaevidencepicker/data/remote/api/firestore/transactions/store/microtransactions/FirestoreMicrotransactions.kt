package com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.store.microtransactions

import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.store.FirestoreMarketplace.Companion.storeCollection
import com.google.firebase.firestore.DocumentReference

class FirestoreMicrotransactions {
    companion object {
        private const val DOCUMENT_MICRO_TRANSACTIONS = "Microtransactions"

        val microTransactionsDocument: DocumentReference
            get() = storeCollection
                .document(DOCUMENT_MICRO_TRANSACTIONS)
    }

}
