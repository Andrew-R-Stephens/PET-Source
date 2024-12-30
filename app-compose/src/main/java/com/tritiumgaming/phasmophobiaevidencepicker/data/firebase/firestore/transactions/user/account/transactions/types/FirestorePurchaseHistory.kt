package com.tritiumgaming.phasmophobiaevidencepicker.data.firebase.firestore.transactions.user.account.transactions.types

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.data.firebase.firestore.transactions.user.account.transactions.FirestoreTransactionHistory.Companion.transactionHistoryDocument
import com.tritiumgaming.phasmophobiaevidencepicker.data.listeners.firestore.OnFirestoreProcessListener

class FirestorePurchaseHistory {

    companion object {
        private const val COLLECTION_PURCHASE_HISTORY = "PurchaseHistory"
        private const val DOCUMENT_PURCHASED_ITEM = "PurchaseItem"

        private const val FIELD_DATE_PURCHASED = "datePurchased"

        private const val FIELD_PURCHASE_REFERENCE = "product_ref"
        private const val FIELD_ORDER_ID = "order_id"

        @Throws(Exception::class)
        fun init() {
            purchaseDocument
                .set(HashMap<String, Any>(), SetOptions.merge())
                .addOnSuccessListener { unused: Void? ->
                    Log.d(
                        "Firestore",
                        "$COLLECTION_PURCHASE_HISTORY successfully INITIALIZED!"
                    )
                }
                .addOnFailureListener { e: Exception ->
                    Log.d(
                        "Firestore",
                        COLLECTION_PURCHASE_HISTORY + "User Unlock History failed INITIALIZATION"
                    )
                    e.printStackTrace()
                }
                .addOnCompleteListener { task: Task<Void?>? ->
                    Log.d(
                        "Firestore",
                        COLLECTION_PURCHASE_HISTORY + "User Unlock History INITIALIZATION process complete!"
                    )
                }
        }

        private val purchaseHistoryCollection: CollectionReference
            @Throws(Exception::class)
            get() = transactionHistoryDocument.collection(COLLECTION_PURCHASE_HISTORY)

        private val purchaseDocument: DocumentReference
            @Throws(Exception::class)
            get() {
                return purchaseHistoryCollection.document(DOCUMENT_PURCHASED_ITEM)
            }

        @Throws(Exception::class)
        fun addPurchaseDocument(
            purchaseReferenceDoc: DocumentReference?,
            orderID: String?,
            callback: OnFirestoreProcessListener?,
        ) {
            if (purchaseReferenceDoc == null || orderID == null) {
                return
            }

            val documentData: MutableMap<String, Any> = HashMap()
            documentData[FIELD_PURCHASE_REFERENCE] = purchaseReferenceDoc
            documentData[FIELD_ORDER_ID] = orderID
            documentData[FIELD_DATE_PURCHASED] = Timestamp.now()

            purchaseHistoryCollection.add(documentData)
                .addOnSuccessListener {
                    callback?.onSuccess()
                    Log.d(
                        "Firestore",
                        "Purchase document of ${ purchaseReferenceDoc.id } GENERATED / LOCATED!"
                    )
                }
                .addOnFailureListener { e: Exception ->
                    callback?.onFailure()
                    Log.d(
                        "Firestore",
                        "Purchase document of ${ purchaseReferenceDoc.id } could NOT be GENERATED / LOCATED!"
                    )
                    e.printStackTrace()
                }
                .addOnCompleteListener {
                    callback?.onComplete()
                    Log.d(
                        "Firestore",
                        "Purchase document of ${ purchaseReferenceDoc.id } process complete."
                    )
                }
        }
    }
}
