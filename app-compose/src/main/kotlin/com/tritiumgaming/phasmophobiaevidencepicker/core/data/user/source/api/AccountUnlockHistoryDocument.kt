package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.AccountUnlockHistoryCollection.Companion.COLLECTION_UNLOCK_HISTORY
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.AccountUnlockHistoryCollection.Companion.unlockHistoryCollection

class AccountUnlockHistoryDocument {

    companion object {

        private const val DOCUMENT_UNLOCKED_ITEM = "UnlockedItem"

        internal const val FIELD_DATE_UNLOCKED = "dateUnlocked"
        internal const val FIELD_TYPE = "type"

        private val unlockedDocument: DocumentReference
            @Throws(Exception::class)
            get() = unlockHistoryCollection.document(DOCUMENT_UNLOCKED_ITEM)


        @Throws(Exception::class)
        fun init() {
            unlockedDocument.get()
                .addOnSuccessListener { unused: DocumentSnapshot? ->
                    Log.d("Firestore",
                        "$COLLECTION_UNLOCK_HISTORY successfully INITIALIZED!")
                }
                .addOnFailureListener { e: Exception ->
                    Log.d("Firestore",
                        "$COLLECTION_UNLOCK_HISTORY User Unlock History failed INITIALIZATION")
                    e.printStackTrace()
                }
                .addOnCompleteListener { task: Task<DocumentSnapshot?>? ->
                    Log.d("Firestore",
                        "$COLLECTION_UNLOCK_HISTORY User Unlock History INITIALIZATION process complete!")
                }
        }

    }

}
