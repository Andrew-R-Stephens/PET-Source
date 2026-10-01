package com.tritiumgaming.data.marketplace.billable.model

import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryFilterField
import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryFilterValue
import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryFilterValue.NONE
import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryLimit
import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryOrderDirection
import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryOrderField

actual class BillableQueryOptions {

    actual val filterField: BillableQueryFilterField
    actual val filterValue: BillableQueryFilterValue
    actual val orderField: BillableQueryOrderField
    actual val orderDirection: BillableQueryOrderDirection
    actual val limit: BillableQueryLimit

    actual constructor() {
        this.filterField = BillableQueryFilterField.NONE
        this.filterValue = NONE
        this.orderField = BillableQueryOrderField.NONE
        this.orderDirection = BillableQueryOrderDirection.DESCENDING
        this.limit = BillableQueryLimit.SAFE_LIMIT
    }

    actual constructor(
        filterField: BillableQueryFilterField?,
        filterValue: BillableQueryFilterValue?,
        orderField: BillableQueryOrderField?,
        orderDirection: BillableQueryOrderDirection?,
        limit: BillableQueryLimit?
    ) {
        this.filterField = filterField ?: BillableQueryFilterField.NONE
        this.filterValue = filterValue ?: NONE
        this.orderField = orderField ?: BillableQueryOrderField.NONE
        this.orderDirection = orderDirection ?: BillableQueryOrderDirection.DESCENDING
        this.limit = limit ?: BillableQueryLimit.SAFE_LIMIT
    }

}
