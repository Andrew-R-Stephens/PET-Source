package com.tritiumgaming.shared.data.market.billable.model.query

actual enum class BillableQueryLimit(val value: Int) {
    SAFE_LIMIT(50),
    UNLIMITED(Int.MAX_VALUE)
}