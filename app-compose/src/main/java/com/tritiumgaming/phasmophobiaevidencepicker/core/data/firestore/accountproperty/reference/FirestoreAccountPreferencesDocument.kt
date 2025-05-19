package com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountproperty.reference

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.account.source.model.FirestoreAccountCollection.Companion.accountCollection

class FirestoreAccountPreferencesDocument {

    companion object {

        private const val DOCUMENT_PREFERENCES: String = "Preferences"

        internal const val FIELD_MARKETPLACE_AGREEMENT_SHOWN: String = "marketplaceAgreementShown"

        val preferencesDocument: DocumentReference
            @Throws(Exception::class)
            get() = accountCollection.document(DOCUMENT_PREFERENCES)

        @Throws(Exception::class)
        fun init() {
            val preferencesMap: MutableMap<Any, Any> = HashMap()

            preferencesDocument
                .get()
                .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                    if (documentSnapshot[FIELD_MARKETPLACE_AGREEMENT_SHOWN] == null) {
                        preferencesMap[FIELD_MARKETPLACE_AGREEMENT_SHOWN] = false
                    }
                    documentSnapshot.reference.set(preferencesMap, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d(
                                "Firestore",
                                "User Preferences successfully INITIALIZED!"
                            )
                        }
                        .addOnFailureListener { e: Exception ->
                            Log.d("Firestore", "User Preferences failed INITIALIZATION")
                            e.printStackTrace()
                        }
                        .addOnCompleteListener { task: Task<Void?>? ->
                            Log.d(
                                "Firestore",
                                "User Preferences INITIALIZATION process complete!"
                            )
                        }
                }
                .addOnFailureListener { obj: Exception -> obj.printStackTrace() }
        }

    }
}