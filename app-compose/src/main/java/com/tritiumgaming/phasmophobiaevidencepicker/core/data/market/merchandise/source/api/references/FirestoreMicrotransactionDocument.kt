package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.references

import com.google.firebase.firestore.DocumentReference

class FirestoreMicrotransactionDocument {

    companion object {
        private const val DOCUMENT_MICRO_TRANSACTIONS = "Microtransactions"

        val microTransactionsDocument: DocumentReference
            get() = FirestoreMarketplaceCollection.Companion.storeCollection
                .document(DOCUMENT_MICRO_TRANSACTIONS)
    }

}
