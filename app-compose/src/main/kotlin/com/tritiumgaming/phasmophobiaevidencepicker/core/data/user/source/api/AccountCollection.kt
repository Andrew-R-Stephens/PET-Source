package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api

import com.google.firebase.firestore.CollectionReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.UserDocument.Companion.userDocument

class AccountCollection {

    companion object {
        private const val COLLECTION_ACCOUNT: String = "Account"

        val accountCollection: CollectionReference
            @Throws(Exception::class)
            get() = userDocument.collection(COLLECTION_ACCOUNT)

        @Throws(Exception::class)
        fun init() {
            AccountCreditDocument.Companion.init()
            AccountPreferencesDocument.Companion.init()
            AccountTransactionHistoryDocument.Companion.init()
        }

    }
}