package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

class MarketMerchandiseFirestoreDataSource {

    fun getMerchandiseDocument(
        storeCollection: CollectionReference
    ): DocumentReference = storeCollection
        .document(DOCUMENT_MERCHANDISE)

    private companion object {
        private const val DOCUMENT_MERCHANDISE = "Merchandise"
    }

}