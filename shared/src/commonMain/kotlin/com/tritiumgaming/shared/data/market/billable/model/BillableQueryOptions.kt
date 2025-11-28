package com.tritiumgaming.shared.data.market.billable.model

import com.tritiumgaming.shared.data.market.billable.model.query.BillableQueryFilterField
import com.tritiumgaming.shared.data.market.billable.model.query.BillableQueryFilterValue
import com.tritiumgaming.shared.data.market.billable.model.query.BillableQueryLimit
import com.tritiumgaming.shared.data.market.billable.model.query.BillableQueryOrderDirection
import com.tritiumgaming.shared.data.market.billable.model.query.BillableQueryOrderField

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