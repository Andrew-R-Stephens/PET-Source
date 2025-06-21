package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.MarketBillableDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.BillableQueryOptions

interface MarketBillableRemoteDataSource {

    suspend fun fetch(
        billableQueryOptions: BillableQueryOptions = BillableQueryOptions()
    ): Result<List<MarketBillableDto>>

}