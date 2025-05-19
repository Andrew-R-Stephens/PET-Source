package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.references

import com.google.firebase.firestore.CollectionReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.references.FirestoreMicrotransactionDocument.Companion.microTransactionsDocument

class FirestoreBillableCollection {

    companion object {
        private const val COLLECTION_BILLABLE = "Billables"
        
        val billableCollection: CollectionReference
            get() = microTransactionsDocument
                .collection(COLLECTION_BILLABLE)

    }

}
