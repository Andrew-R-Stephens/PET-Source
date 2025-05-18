package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.user.account

import com.google.firebase.firestore.CollectionReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.user.FirestoreUser.Companion.userDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.user.account.properties.FirestoreAccountCredit
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.user.account.properties.FirestoreAccountPreferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.user.account.transactions.FirestoreTransactionHistory

class FirestoreAccount {
    companion object {
        private const val COLLECTION_ACCOUNT: String = "Account"

        val accountCollection: CollectionReference
            @Throws(Exception::class)
            get() = userDocument.collection(COLLECTION_ACCOUNT)

        @Throws(Exception::class)
        fun init() {
            FirestoreAccountCredit.init()
            FirestoreAccountPreferences.init()
            FirestoreTransactionHistory.init()
        }

    }
}
