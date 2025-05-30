package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.api

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.references.FirestoreMerchandiseDocument

class MarketTypographyApi {

    companion object {
        private const val COLLECTION_TYPOGRAPHYS = "Typographys"

        private val typographyCollection: CollectionReference
            get() = FirestoreMerchandiseDocument.Companion.merchandiseDocument.collection(COLLECTION_TYPOGRAPHYS)

        fun getTypographysWhere(
            filterField: String = "group", value: String? = null,
            orderField: String? = null,
            order: Query.Direction? = Query.Direction.DESCENDING
        ): Task<QuerySnapshot> {
            val query = typographyCollection
                .whereEqualTo(filterField, value)

            if (orderField == null || order == null) {
                return query.get()
            }

            return query
                .orderBy(orderField, order)
                .get()
        }

        fun getAllTypographys(
            orderField: String? = null,
            order: Query.Direction? = Query.Direction.DESCENDING
        ): Task<QuerySnapshot> {
            val query = typographyCollection

            if (orderField == null || order == null) {
                return query.get()
            }

            return query
                .orderBy(orderField, order)
                .get()
        }
    }

}