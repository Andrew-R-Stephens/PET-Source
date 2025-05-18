package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.user.account.transactions

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.user.account.FirestoreAccount.Companion.accountCollection

class FirestoreTransactionHistory {

    companion object {
        private const val DOCUMENT_TRANSACTION_HISTORY = "TransactionHistory"

        @get:Throws(Exception::class)
        val transactionHistoryDocument: DocumentReference
            get() = accountCollection.document(DOCUMENT_TRANSACTION_HISTORY)

        @Throws(Exception::class)
        fun init() {
            val emptyMap: Map<Any, Any> = HashMap()

            transactionHistoryDocument
                .get()
                .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                    documentSnapshot.reference.set(emptyMap, SetOptions.merge())
                        .addOnSuccessListener { unused: Void? ->
                            Log.d(
                                "Firestore",
                                DOCUMENT_TRANSACTION_HISTORY +
                                        " successfully INITIALIZED!"
                            )
                        }
                        .addOnFailureListener { e: Exception ->
                            Log.d(
                                "Firestore",
                                DOCUMENT_TRANSACTION_HISTORY +
                                        " failed INITIALIZATION"
                            )
                            e.printStackTrace()
                        }
                        .addOnCompleteListener { task: Task<Void?>? ->
                            Log.d(
                                "Firestore",
                                DOCUMENT_TRANSACTION_HISTORY +
                                        " INITIALIZATION process complete!"
                            )
                        }
                }
                .addOnFailureListener { obj: Exception -> obj.printStackTrace() }
        }

    }
}
