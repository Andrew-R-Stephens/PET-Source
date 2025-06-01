package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.MarketBillableDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource.BillableQueryOptions

interface MarketBillableRepository {

    suspend fun fetchBillables(
        billableQueryOptions: BillableQueryOptions = BillableQueryOptions()
    ): List<MarketBillableDto>

}