package com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.account.source.model

import com.google.firebase.firestore.CollectionReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountcredit.reference.FirestoreCreditDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountproperty.reference.FirestoreAccountPreferencesDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accounttransactionhistory.reference.FirestoreTransactionHistoryDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.user.reference.FirestoreUserDocument.Companion.userDocument

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