package com.tritiumgaming.phasmophobiaevidencepicker.data.firebase.firestore.transactions.user.account.properties

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.data.firebase.firestore.transactions.user.account.FirestoreAccount.Companion.accountCollection
import com.tritiumgaming.phasmophobiaevidencepicker.data.listeners.firestore.OnFirestoreProcessListener

class FirestoreAccountCredit {
    companion object {

        private const val DOCUMENT_CREDITS: String = "Credits"

        private const val FIELD_CREDITS_SPENT: String = "spentCredits"
        const val FIELD_CREDITS_EARNED: String = "earnedCredits"

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

        @JvmOverloads
        @Throws(Exception::class)
        fun addCredits(creditAmount: Long, callback: OnFirestoreProcessListener? = null) {
            val creditDocument = creditsDocument

            val data: MutableMap<String, Any> = HashMap()
            data[FIELD_CREDITS_EARNED] = FieldValue.increment(creditAmount)

            creditDocument.update(data)
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

        @JvmOverloads
        @Throws(Exception::class)
        fun removeCredits(creditAmount: Long, callback: OnFirestoreProcessListener? = null) {
            val creditDocument = creditsDocument

            creditDocument.get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
                val storedCredits = task.result.get(FIELD_CREDITS_EARNED, Long::class.java)
                if (storedCredits != null && storedCredits < creditAmount) {
                    callback?.onFailure()

                    return@addOnCompleteListener
                }

                val data: MutableMap<String, Any> = HashMap()
                data[FIELD_CREDITS_EARNED] = FieldValue.increment(-creditAmount)
                data[FIELD_CREDITS_SPENT] = FieldValue.increment(creditAmount)

                task.result.reference.update(data)
                    .addOnSuccessListener { result: Void? ->
                        callback?.onSuccess()
                    }
                    .addOnFailureListener { error: Exception ->
                        callback?.onFailure()
                        error.printStackTrace()
                    }
                    .addOnCompleteListener { result: Task<Void?>? ->
                        callback?.onComplete()
                    }
                callback?.onComplete()
            }
        }
    }
}
