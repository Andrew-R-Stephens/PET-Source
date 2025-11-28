package com.tritiumgaming.shared.data.market.billable.model.query

import com.google.firebase.firestore.Query

actual enum class BillableQueryOrderDirection(val value: Query.Direction?) {
    ASCENDING(Query.Direction.ASCENDING),
    DESCENDING(Query.Direction.DESCENDING)
}