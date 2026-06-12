package com.tritiumgaming.shared.data.market.billable.model

import com.tritiumgaming.shared.data.market.billable.model.query.BillableQueryFilterField
import com.tritiumgaming.shared.data.market.billable.model.query.BillableQueryFilterValue
import com.tritiumgaming.shared.data.market.billable.model.query.BillableQueryLimit
import com.tritiumgaming.shared.data.market.billable.model.query.BillableQueryOrderDirection
import com.tritiumgaming.shared.data.market.billable.model.query.BillableQueryOrderField

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
