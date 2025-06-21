package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.mapper.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.BillableQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.MarketBillable
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.repository.MarketBillableRepository

class MarketBillableRepositoryImpl(
    private val billableFirestoreDataSource: MarketBillableFirestoreDataSource,
): MarketBillableRepository {

    /**
     * Fetches remote Billables from the Firestore database.
     * @param billableQueryOptions This is an optional parameter that allows you to specify filter
     * and ordering options. This can be left empty to fetch all Billables without any filtering.
     * Ordering defaults to [Query.Direction.DESCENDING].
     */
    override suspend fun fetchBillables(
        billableQueryOptions: BillableQueryOptions
    ): Result<List<MarketBillable>> =
        billableFirestoreDataSource.fetch(billableQueryOptions).map { it.toDomain() }

}