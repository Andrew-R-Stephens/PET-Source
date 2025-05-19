package com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.account.source.model

import com.google.firebase.firestore.CollectionReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountcredit.reference.FirestoreCreditDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountproperty.reference.FirestoreAccountPreferencesDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accounttransactionhistory.reference.FirestoreTransactionHistoryDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.user.reference.FirestoreUserDocument.Companion.userDocument

class FirestoreAccountCollection {

    companion object {
        private const val COLLECTION_ACCOUNT: String = "Account"

        val accountCollection: CollectionReference
            @Throws(Exception::class)
            get() = userDocument.collection(COLLECTION_ACCOUNT)

        @Throws(Exception::class)
        fun init() {
            FirestoreCreditDocument.Companion.init()
            FirestoreAccountPreferencesDocument.Companion.init()
            FirestoreTransactionHistoryDocument.Companion.init()
        }

    }
}