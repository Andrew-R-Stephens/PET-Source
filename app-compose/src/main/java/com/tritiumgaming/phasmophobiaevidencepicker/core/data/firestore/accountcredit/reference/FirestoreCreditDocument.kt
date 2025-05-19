package com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountcredit.reference

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.account.source.model.FirestoreAccountCollection.Companion.accountCollection

class FirestoreCreditDocument {
    companion object {

        private const val DOCUMENT_CREDITS: String = "Credits"

        internal const val FIELD_CREDITS_SPENT: String = "spentCredits"
        internal const val FIELD_CREDITS_EARNED: String = "earnedCredits"

        val creditsDocument: DocumentReference
            @Throws(Exception::class)
            get() = accountCollection.document(DOCUMENT_CREDITS)

        @Throws(Exception::class)
        fun init() {
            val creditsMap: MutableMap<Any, Any> = HashMap()

            creditsDocument
                .get()
                .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                    if (documentSnapshot[FIELD_CREDITS_EARNED] == null) {
                        creditsMap[FIELD_CREDITS_EARNED] = 0
                    }
                    if (documentSnapshot[FIELD_CREDITS_SPENT] == null) {
                        creditsMap[FIELD_CREDITS_SPENT] = 0
                    }
                    documentSnapshot.reference.set(creditsMap, SetOptions.merge())
                        .addOnSuccessListener { unused: Void? ->
                            Log.d(
                                "Firestore",
                                "User Credits successfully INITIALIZED!"
                            )
                        }
                        .addOnFailureListener { e: Exception ->
                            Log.d("Firestore", "User Credits failed INITIALIZATION")
                            e.printStackTrace()
                        }
                        .addOnCompleteListener { task: Task<Void?>? ->
                            Log.d(
                                "Firestore",
                                "User Credits INITIALIZATION process complete!"
                            )
                        }
                }
                .addOnFailureListener { obj: Exception -> obj.printStackTrace() }
        }

    }
}