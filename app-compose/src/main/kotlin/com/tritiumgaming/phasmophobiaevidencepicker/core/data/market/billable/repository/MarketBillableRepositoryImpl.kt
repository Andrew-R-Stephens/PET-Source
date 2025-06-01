package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.MarketBillableDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource.BillableQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.remote.MarketFirestoreDataSource

class MarketBillableRepositoryImpl(
    private val marketFirestoreDataSource: MarketFirestoreDataSource,
    private val billableFirestoreDataSource: MarketBillableFirestoreDataSource,
) {

    suspend fun fetchBillables(): List<MarketBillableDto> {

        val storeCollectionRef = marketFirestoreDataSource.storeCollectionRef

        return billableFirestoreDataSource.query(storeCollectionRef)

    }

    suspend fun queryBillables(
        billableQueryOptions: BillableQueryOptions
    ): List<MarketBillableDto> {

        val storeCollectionRef = marketFirestoreDataSource.storeCollectionRef

        return billableFirestoreDataSource.query(
            storeCollectionRef, billableQueryOptions)

    }

}