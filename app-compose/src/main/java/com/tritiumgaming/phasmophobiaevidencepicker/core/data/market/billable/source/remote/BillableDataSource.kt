package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.references.FirestoreBillableCollection.Companion.billableCollection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.NetworkBillableEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.source.BillableDataSource

class BillableRemoteDataSource: BillableDataSource<NetworkBillableEntity> {

    override fun getBillableWhere(
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