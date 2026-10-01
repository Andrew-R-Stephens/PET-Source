package com.tritiumgaming.data.marketplace.billable.model

import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryFilterField
import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryFilterValue
import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryLimit
import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryOrderDirection
import com.tritiumgaming.data.marketplace.billable.model.query.BillableQueryOrderField

expect class BillableQueryOptions(
    filterField: BillableQueryFilterField?,
    filterValue: BillableQueryFilterValue?,
    orderField: BillableQueryOrderField?,
    orderDirection: BillableQueryOrderDirection?,
    limit: BillableQueryLimit?
) {

    val filterField: BillableQueryFilterField
    val filterValue: BillableQueryFilterValue
    val orderField: BillableQueryOrderField
    val orderDirection: BillableQueryOrderDirection
    val limit: BillableQueryLimit

    constructor()

}