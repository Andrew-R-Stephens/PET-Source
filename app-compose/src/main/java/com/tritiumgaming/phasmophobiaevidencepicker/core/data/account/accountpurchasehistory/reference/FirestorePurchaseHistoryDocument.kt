package com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountpurchasehistory.reference

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountpurchasehistory.reference.FirestorePurchaseHistoryCollection.Companion.COLLECTION_PURCHASE_HISTORY
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountpurchasehistory.reference.FirestorePurchaseHistoryCollection.Companion.purchaseHistoryCollection

class FirestorePurchaseHistoryDocument {

    companion object {

        private const val DOCUMENT_PURCHASED_ITEM = "PurchaseItem"

        internal const val FIELD_DATE_PURCHASED = "datePurchased"

        internal const val FIELD_PURCHASE_REFERENCE = "product_ref"
        internal const val FIELD_ORDER_ID = "order_id"

        private val purchaseDocument: DocumentReference
            @Throws(Exception::class)
            get() {
                return purchaseHistoryCollection.document(DOCUMENT_PURCHASED_ITEM)
            }

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
                        "$COLLECTION_PURCHASE_HISTORY User Unlock History failed INITIALIZATION"
                    )
                    e.printStackTrace()
                }
                .addOnCompleteListener { task: Task<Void?>? ->
                    Log.d(
                        "Firestore",
                        "$COLLECTION_PURCHASE_HISTORY User Unlock History INITIALIZATION process complete!"
                    )
                }
        }

    }
}
