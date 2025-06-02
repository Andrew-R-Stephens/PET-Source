package com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.store.merchandise.bundles

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.store.merchandise.FirestoreMerchandise.Companion.merchandiseDocument

class FirestoreMerchandiseBundle {

    companion object {
        private const val COLLECTION_BUNDLES = "Bundles"

        private val bundlesCollection: CollectionReference
            get() = merchandiseDocument.collection(COLLECTION_BUNDLES)

        fun getBundleWhere(
            filterField: String? = null,
            value: String? = null,
            orderField: String? = null,
            order: Query.Direction? = Query.Direction.DESCENDING
        ): Task<QuerySnapshot> {
            var query: Query = bundlesCollection
            if (filterField != null && value != null) {
                query = bundlesCollection
                    .whereEqualTo(filterField, value)
            }

            if (orderField != null && order != null) {
                return query
                    .orderBy(orderField, order)
                    .get()
            }

            return query.get()
        }
    }

}
