package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.MarketBillableDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource.BillableQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.MarketBundleFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.MarketBundleFirestoreDataSource.BundleQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.remote.MarketFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.remote.MarketMerchandiseFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository.BundleRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource

class BundleRepositoryImpl(
    private val marketFirestoreDataSource: MarketFirestoreDataSource,
    private val merchandiseFirestoreDataSource: MarketMerchandiseFirestoreDataSource,
    private val marketBundleDataSource: MarketBundleFirestoreDataSource
) {

    suspend fun fetchBundles(): List<MarketBundleDto> {

        val storeCollectionRef = marketFirestoreDataSource.storeCollectionRef
        val merchandiseDocument = merchandiseFirestoreDataSource
            .getMerchandiseDocument(storeCollectionRef)

        return marketBundleDataSource.query(
            merchandiseDocument
        )

    }

    suspend fun queryBillables(
        bundleQueryOptions: BundleQueryOptions
    ): List<MarketBundleDto> {

        val storeCollectionRef = marketFirestoreDataSource.storeCollectionRef
        val merchandiseDocument = merchandiseFirestoreDataSource
            .getMerchandiseDocument(storeCollectionRef)

        return marketBundleDataSource.query(
            merchandiseDocument,
            bundleQueryOptions
        )

    }

}