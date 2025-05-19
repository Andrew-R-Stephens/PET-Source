package com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountcredit.source.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountcredit.reference.FirestoreCreditDocument.Companion.FIELD_CREDITS_EARNED
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountcredit.reference.FirestoreCreditDocument.Companion.FIELD_CREDITS_SPENT
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.accountcredit.reference.FirestoreCreditDocument.Companion.creditsDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener

class FirestoreCreditRemoteDataSource {

    @JvmOverloads
    @Throws(Exception::class)
    fun addCredits(creditAmount: Long, callback: OnFirestoreProcessListener? = null) {
        val creditDocument =
            creditsDocument

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