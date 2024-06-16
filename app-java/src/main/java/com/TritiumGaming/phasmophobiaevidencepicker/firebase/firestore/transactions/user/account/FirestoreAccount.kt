package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.userDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.FirestoreTransactionHistory
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit
import com.google.firebase.firestore.CollectionReference

class FirestoreAccount {
    companion object {
        private const val COLLECTION_ACCOUNT: String = "Account"

        @Throws(Exception::class)
        fun init() {
            FirestoreAccountCredit.init()
            FirestoreTransactionHistory.init()
        }

        val accountCollection: CollectionReference
            @Throws(Exception::class)
            get() = userDocument.collection(COLLECTION_ACCOUNT)
    }
}
