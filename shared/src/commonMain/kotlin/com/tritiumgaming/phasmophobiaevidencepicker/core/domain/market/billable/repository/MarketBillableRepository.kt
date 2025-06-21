package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.BillableQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.MarketBillable

interface MarketBillableRepository {

    suspend fun fetchBillables(
        billableQueryOptions: BillableQueryOptions = BillableQueryOptions()
    ): Result<List<MarketBillable>>

}