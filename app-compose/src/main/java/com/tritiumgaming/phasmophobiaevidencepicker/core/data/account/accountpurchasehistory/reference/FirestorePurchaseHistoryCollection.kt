package com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountpurchasehistory.reference

import com.google.firebase.firestore.CollectionReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accounttransactionhistory.reference.FirestoreTransactionHistoryDocument.Companion.transactionHistoryDocument

class FirestorePurchaseHistoryCollection {

    companion object {

        internal const val COLLECTION_PURCHASE_HISTORY = "PurchaseHistory"

        val purchaseHistoryCollection: CollectionReference
            @Throws(Exception::class)
            get() = transactionHistoryDocument.collection(COLLECTION_PURCHASE_HISTORY)

    }
}
