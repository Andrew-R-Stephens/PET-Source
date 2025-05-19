package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.firestore.source

import com.google.firebase.firestore.DocumentReference
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.OnFirestoreProcessListener

interface PurchaseHistoryDataSource {

    fun addPurchaseDocument(
        purchaseReferenceDoc: DocumentReference?,
        orderID: String?,
        callback: OnFirestoreProcessListener?,
    )
    
}