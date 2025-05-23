package com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountunlockhistory.reference

import com.google.firebase.firestore.CollectionReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accounttransactionhistory.reference.FirestoreTransactionHistoryDocument.Companion.transactionHistoryDocument

class FirestoreUnlockHistoryCollection {

    companion object {

        const val COLLECTION_UNLOCK_HISTORY = "UnlockHistory"

        val unlockHistoryCollection: CollectionReference
            @Throws(Exception::class)
            get() = transactionHistoryDocument.collection(COLLECTION_UNLOCK_HISTORY)

    }

}
