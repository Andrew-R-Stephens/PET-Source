package com.tritiumgaming.shared.data.market.billable.model

actual class BillableQueryOptions {
    actual val filterField: BillableQueryFilterField
        get() = TODO("Not yet implemented")
    actual val filterValue: BillableQueryFilterValue
        get() = TODO("Not yet implemented")
    actual val orderField: BillableQueryOrderField
        get() = TODO("Not yet implemented")
    actual val orderDirection: BillableQueryOrderDirection
        get() = TODO("Not yet implemented")
    actual val limit: BillableQueryLimit
        get() = TODO("Not yet implemented")

    actual enum class BillableQueryFilterField { TYPE, NONE }
    actual enum class BillableQueryFilterValue { CREDITS, NONE }
    actual enum class BillableQueryOrderField { NONE }
    actual enum class BillableQueryOrderDirection { ASCENDING, DESCENDING }
    actual enum class BillableQueryLimit { SAFE_LIMIT, UNLIMITED }

    actual constructor() {
        TODO("Not yet implemented")
    }

    actual constructor(
        filterField: BillableQueryFilterField?,
        filterValue: BillableQueryFilterValue?,
        orderField: BillableQueryOrderField?,
        orderDirection: BillableQueryOrderDirection?,
        limit: BillableQueryLimit?
    ) {
        TODO("Not yet implemented")
    }
}