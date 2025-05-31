package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.references

import com.google.firebase.firestore.DocumentReference

class FirestoreMerchandiseDocument {

    companion object {
        private const val DOCUMENT_MERCHANDISE = "Merchandise"

        val merchandiseDocument: DocumentReference
            get() = FirestoreMarketplaceCollection.Companion.storeCollection.document(DOCUMENT_MERCHANDISE)
    }

}