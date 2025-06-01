package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MarketFirestoreDataSource(
    private val firestore: FirebaseFirestore
) {

    val storeCollectionRef: CollectionReference
        get() = firestore.collection(COLLECTION_STORE)

    private companion object {
        private const val COLLECTION_STORE = "Store"
    }
}
