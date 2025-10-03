package com.tritiumgaming.data.marketplace.billable.source

import com.tritiumgaming.data.marketplace.billable.dto.MarketBillableDto
import com.tritiumgaming.shared.core.domain.market.billable.model.BillableQueryOptions

interface MarketBillableRemoteDataSource {

    suspend fun fetch(
        billableQueryOptions: BillableQueryOptions = BillableQueryOptions()
    ): Result<List<MarketBillableDto>>

}