package com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountpurchasehistory.source.remote

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountpurchasehistory.reference.FirestorePurchaseHistoryCollection.Companion.purchaseHistoryCollection
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountpurchasehistory.reference.FirestorePurchaseHistoryDocument.Companion.FIELD_DATE_PURCHASED
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountpurchasehistory.reference.FirestorePurchaseHistoryDocument.Companion.FIELD_ORDER_ID
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.account.accountpurchasehistory.reference.FirestorePurchaseHistoryDocument.Companion.FIELD_PURCHASE_REFERENCE
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.firestore.source.PurchaseHistoryDataSource

class PurchaseHistoryRemoteDataSource: PurchaseHistoryDataSource {

    @Throws(Exception::class)
    override fun addPurchaseDocument(
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
        documentData[FIELD_DATE_PURCHASED] = Timestamp.Companion.now()

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
