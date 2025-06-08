package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.MarketBillableDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource.BillableQueryOptions

interface MarketBillableRemoteDataSource {

    suspend fun fetch(
        billableQueryOptions: BillableQueryOptions = BillableQueryOptions()
    ): Result<List<MarketBillableDto>>

}