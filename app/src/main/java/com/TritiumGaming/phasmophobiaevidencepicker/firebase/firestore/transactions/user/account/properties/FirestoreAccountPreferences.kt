package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties

import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreAccount.Companion.accountCollection
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit.Companion.creditsDocument
import com.TritiumGaming.phasmophobiaevidencepicker.listeners.firestore.OnFirestoreProcessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions

class FirestoreAccountPreferences {

    companion object {

        private const val DOCUMENT_PREFERENCES: String = "Preferences"

        const val FIELD_MARKETPLACE_AGREEMENT_SHOWN: String = "marketplaceAgreementShown"

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

        @JvmOverloads
        @Throws(Exception::class)
        fun setMarketplaceAgreementState(
            shown: Boolean,
            callback: OnFirestoreProcessListener? = null) {

            val preferenceDocument = preferencesDocument

            val data: MutableMap<String, Any> = HashMap()
            data[FIELD_MARKETPLACE_AGREEMENT_SHOWN] = shown

            preferenceDocument.update(data)
                .addOnSuccessListener {
                    callback?.onSuccess()
                }
                .addOnFailureListener {
                    callback?.onFailure()
                }
                .addOnCompleteListener {
                    callback?.onComplete()
                }
        }
    }
}