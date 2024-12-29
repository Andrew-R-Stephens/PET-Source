package com.tritiumgaming.phasmophobiaevidencepicker.data.firebase.firestore.transactions.user.account.transactions.types

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.FirestoreTransactionHistory.Companion.transactionHistoryDocument
import com.tritiumgaming.phasmophobiaevidencepicker.data.listeners.firestore.OnFirestoreProcessListener

class FirestoreUnlockHistory {

    companion object {
        private const val COLLECTION_UNLOCK_HISTORY = "UnlockHistory"

        private const val DOCUMENT_UNLOCKED_ITEM = "UnlockedItem"

        private const val FIELD_DATE_UNLOCKED = "dateUnlocked"
        private const val FIELD_TYPE = "type"

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

        val unlockHistoryCollection: CollectionReference
            @Throws(Exception::class)
            get() = transactionHistoryDocument.collection(COLLECTION_UNLOCK_HISTORY)

        private val unlockedDocument: DocumentReference
            @Throws(Exception::class) get() {
                val purchaseHistoryCollection = unlockHistoryCollection

                return purchaseHistoryCollection.document(DOCUMENT_UNLOCKED_ITEM)
            }

        @Throws(Exception::class)
        fun addUnlockDocument(
            unlockUUID: String?, type: String, callback: OnFirestoreProcessListener
        ) {
            if (unlockUUID == null) {
                return
            }

            val documentData: MutableMap<String, Any> = HashMap()
            documentData[FIELD_TYPE] = type
            documentData[FIELD_DATE_UNLOCKED] = Timestamp.now()

            val unlockDocument = unlockHistoryCollection.document(unlockUUID)

            unlockDocument.set(documentData, SetOptions.merge())
                .addOnSuccessListener {
                    callback.onSuccess()
                    Log.d("Firestore",
                        "Unlocked document of $unlockUUID GENERATED / LOCATED!")
                }
                .addOnFailureListener { e: Exception ->
                    callback.onFailure()
                    Log.d("Firestore",
                        "Unlocked document of $unlockUUID could NOT be GENERATED / LOCATED!")
                    e.printStackTrace()
                }
                .addOnCompleteListener {
                    callback.onComplete()
                    Log.d("Firestore",
                        "Unlocked document of $unlockUUID process complete.")
                }
        }

        @Throws(Exception::class)
        fun addUnlockedDocuments(
            unlockUUIDs: ArrayList<String>?, type: String, callback: OnFirestoreProcessListener
        ) {
            if (unlockUUIDs == null) {
                return
            }

            val documentData: MutableMap<String, Any> = HashMap()
            documentData[FIELD_TYPE] = type
            documentData[FIELD_DATE_UNLOCKED] = Timestamp.now()

            for (uuid in unlockUUIDs) {
                val purchasedDocument = unlockHistoryCollection.document(uuid)
                purchasedDocument.set(documentData, SetOptions.merge())
                    .addOnSuccessListener {
                        callback.onSuccess()
                        Log.d("Firestore",
                            "Unlocked document of $uuid GENERATED / LOCATED!")
                    }
                    .addOnFailureListener { e: Exception ->
                        callback.onFailure()
                        Log.d(
                            "Firestore",
                            "Unlocked document of $uuid could NOT be GENERATED / LOCATED!")
                        e.printStackTrace()
                    }
                    .addOnCompleteListener {
                        callback.onComplete()
                        Log.d("Firestore",
                            "Unlocked document of $uuid process complete.")
                    }
            }
        }
    }

}
