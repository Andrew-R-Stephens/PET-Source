package com.tritiumgaming.shared.data.market.billable.repository

import com.tritiumgaming.shared.data.market.billable.model.BillableQueryOptions
import com.tritiumgaming.shared.data.market.billable.model.MarketBillable

interface MarketBillableRepository {

    suspend fun fetchBillables(
        billableQueryOptions: BillableQueryOptions = BillableQueryOptions()
    ): Result<List<MarketBillable>>

}