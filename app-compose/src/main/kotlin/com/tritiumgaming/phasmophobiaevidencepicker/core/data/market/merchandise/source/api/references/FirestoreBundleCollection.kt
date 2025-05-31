package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.references

import com.google.firebase.firestore.CollectionReference

class FirestoreBundleApi {
    companion object {
        private const val COLLECTION_BUNDLES = "Bundles"

        val bundlesCollection: CollectionReference
            get() = FirestoreMerchandiseDocument.Companion.merchandiseDocument.collection(
                COLLECTION_BUNDLES
            )
    }
}
