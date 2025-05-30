package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api

import com.google.firebase.firestore.CollectionReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.AccountTransactionHistoryDocument.Companion.transactionHistoryDocument

class AccountUnlockHistoryCollection {

    companion object {

        const val COLLECTION_UNLOCK_HISTORY = "UnlockHistory"

        val unlockHistoryCollection: CollectionReference
            @Throws(Exception::class)
            get() = transactionHistoryDocument.collection(COLLECTION_UNLOCK_HISTORY)

    }

}
