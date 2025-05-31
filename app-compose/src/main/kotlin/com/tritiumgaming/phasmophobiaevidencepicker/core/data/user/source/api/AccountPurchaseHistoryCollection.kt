package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api

import com.google.firebase.firestore.CollectionReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.AccountTransactionHistoryDocument.Companion.transactionHistoryDocument

class AccountPurchaseHistoryCollection {

    companion object {

        internal const val COLLECTION_PURCHASE_HISTORY = "PurchaseHistory"

        val purchaseHistoryCollection: CollectionReference
            @Throws(Exception::class)
            get() = transactionHistoryDocument.collection(COLLECTION_PURCHASE_HISTORY)

    }
}
