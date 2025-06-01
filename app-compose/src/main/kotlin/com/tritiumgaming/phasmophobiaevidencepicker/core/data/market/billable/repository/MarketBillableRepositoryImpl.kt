package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.MarketBillableDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource.BillableQueryOptions

class MarketBillableRepositoryImpl(
    private val billableFirestoreDataSource: MarketBillableFirestoreDataSource,
) {

    /**
     * Fetches remote Billables from the Firestore database.
     * @param billableQueryOptions This is an optional parameter that allows you to specify filter
     * and ordering options. This can be left empty to fetch all Billables without any filtering.
     * Ordering defaults to [Query.Direction.DESCENDING].
     */
    suspend fun fetchBillables(
        billableQueryOptions: BillableQueryOptions
    ): List<MarketBillableDto> = billableFirestoreDataSource.query(billableQueryOptions)

}