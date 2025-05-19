package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.references

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreMarketplaceCollection {

    companion object {
        private const val COLLECTION_STORE = "Store"

        val storeCollection: CollectionReference
            get() {
                val db = FirebaseFirestore.getInstance()

                return db.collection(COLLECTION_STORE)
            }
    }
}
