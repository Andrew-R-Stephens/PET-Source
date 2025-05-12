package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.api.firestore.transactions.store.microtransactions.billables

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.api.firestore.transactions.store.microtransactions.FirestoreMicrotransactions.Companion.microTransactionsDocument

class FirestoreMicrotransactionBillables {

    companion object {
        private const val COLLECTION_BILLABLE = "Billables"
        
        val billableCollection: CollectionReference
            get() = microTransactionsDocument
                .collection(COLLECTION_BILLABLE)

        fun getBillablesWhere(
            filterField: String, value: String?,
            orderField: String?,
            order: Query.Direction?
        ): Task<QuerySnapshot> {
            val query = billableCollection
                .whereEqualTo(filterField, value)

            if (orderField == null || order == null) {
                return query.get()
            }

            return query
                .orderBy(orderField, order)
                .get()
        }
    }

}
