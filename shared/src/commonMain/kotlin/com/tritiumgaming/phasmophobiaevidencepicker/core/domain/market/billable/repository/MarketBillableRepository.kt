package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource.BillableQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.MarketBillable

interface MarketBillableRepository {

    suspend fun fetchBillables(
        billableQueryOptions: BillableQueryOptions = BillableQueryOptions()
    ): Result<List<MarketBillable>>

}