package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.userDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountPreferences
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.FirestoreTransactionHistory
import com.google.firebase.firestore.CollectionReference

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
