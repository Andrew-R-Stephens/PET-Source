package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

interface BillableDataSource<T> {

    fun getBillableWhere(
        filterField: String, value: String?,
        orderField: String?,
        order: Query.Direction?
    ): Task<QuerySnapshot>

}