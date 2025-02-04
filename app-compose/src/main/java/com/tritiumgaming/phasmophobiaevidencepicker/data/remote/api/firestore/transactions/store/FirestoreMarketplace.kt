package com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.store

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreMarketplace {

    companion object {
        private const val COLLECTION_STORE = "Store"

        val storeCollection: CollectionReference
            get() {
                val db = FirebaseFirestore.getInstance()

                return db.collection(COLLECTION_STORE)
            }
    }
}
