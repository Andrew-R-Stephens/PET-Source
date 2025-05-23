package com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountunlockhistory.source.remote

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountunlockhistory.reference.FirestoreUnlockHistoryCollection.Companion.unlockHistoryCollection
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountunlockhistory.reference.FirestoreUnlockHistoryDocument.Companion.FIELD_DATE_UNLOCKED
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountunlockhistory.reference.FirestoreUnlockHistoryDocument.Companion.FIELD_TYPE
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.firestore.source.UnlockHistoryDataSource

class UnlockHistoryRemoteDataSource: UnlockHistoryDataSource {

    @Throws(Exception::class)
    override fun addUnlockDocument(
        unlockUUID: String?, type: String, callback: OnFirestoreProcessListener
    ) {
        if (unlockUUID == null) {
            return
        }

        val documentData: MutableMap<String, Any> = HashMap()
        documentData[FIELD_TYPE] = type
        documentData[FIELD_DATE_UNLOCKED] = Timestamp.Companion.now()

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
    override fun addUnlockedDocuments(
        unlockUUIDs: ArrayList<String>?, type: String, callback: OnFirestoreProcessListener
    ) {
        if (unlockUUIDs == null) {
            return
        }

        val documentData: MutableMap<String, Any> = HashMap()
        documentData[FIELD_TYPE] = type
        documentData[FIELD_DATE_UNLOCKED] = Timestamp.Companion.now()

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
