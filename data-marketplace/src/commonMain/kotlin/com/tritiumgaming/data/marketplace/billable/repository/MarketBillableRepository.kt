package com.tritiumgaming.data.marketplace.billable.repository

import com.tritiumgaming.data.marketplace.billable.model.BillableQueryOptions
import com.tritiumgaming.data.marketplace.billable.model.MarketBillable

interface MarketBillableRepository {

    suspend fun fetchBillables(
        billableQueryOptions: BillableQueryOptions = BillableQueryOptions(),
        version: Int
    ): Result<List<MarketBillable>>

}