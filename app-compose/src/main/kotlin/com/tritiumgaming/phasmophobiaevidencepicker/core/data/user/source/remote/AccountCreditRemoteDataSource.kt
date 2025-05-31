package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.AccountCreditDocument.Companion.FIELD_CREDITS_EARNED
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.AccountCreditDocument.Companion.FIELD_CREDITS_SPENT
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.api.AccountCreditDocument.Companion.creditsDocument
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.source.AccountCreditDataSource

class AccountCreditRemoteDataSource: AccountCreditDataSource {

    @Throws(Exception::class)
    override fun addCredits(creditAmount: Long, listener: OnFirestoreProcessListener?) {

        val data: MutableMap<String, Any> = HashMap()
        data[FIELD_CREDITS_EARNED] = FieldValue.increment(creditAmount)

        creditsDocument.update(data)
            .addOnSuccessListener {
                listener?.onSuccess()
            }
            .addOnFailureListener {
                listener?.onFailure()
            }
            .addOnCompleteListener {
                listener?.onComplete()
            }
    }

    @Throws(Exception::class)
    override fun removeCredits(creditAmount: Long, listener: OnFirestoreProcessListener?) {

        creditsDocument
            .get()
            .addOnCompleteListener { task: Task<DocumentSnapshot> ->
                val storedCredits = task.result.get(FIELD_CREDITS_EARNED, Long::class.java)
                if (storedCredits != null && storedCredits < creditAmount) {
                    listener?.onFailure()

                    return@addOnCompleteListener
                }

                val data: MutableMap<String, Any> = HashMap()
                data[FIELD_CREDITS_EARNED] = FieldValue.increment(-creditAmount)
                data[FIELD_CREDITS_SPENT] = FieldValue.increment(creditAmount)

                task.result.reference.update(data)
                    .addOnSuccessListener { result: Void? ->
                        listener?.onSuccess()
                    }
                    .addOnFailureListener { error: Exception ->
                        listener?.onFailure()
                        error.printStackTrace()
                    }
                    .addOnCompleteListener { result: Task<Void?>? ->
                        listener?.onComplete()
                    }
                listener?.onComplete()
            }
    }

}
