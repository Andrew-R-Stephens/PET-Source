package com.tritiumgaming.shared.data.market.billable.repository

interface MarketBillableRepository {

    suspend fun fetchBillables(
        billableQueryOptions: com.tritiumgaming.shared.data.market.billable.model.BillableQueryOptions = com.tritiumgaming.shared.data.market.billable.model.BillableQueryOptions()
    ): Result<List<com.tritiumgaming.shared.data.market.billable.model.MarketBillable>>

}