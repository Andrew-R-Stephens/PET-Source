package com.tritiumgaming.data.marketplace.bundle.source.remote

import com.tritiumgaming.data.marketplace.bundle.dto.MarketBundleDto
import com.tritiumgaming.shared.data.market.bundle.model.BundleQueryOptions

interface MarketBundleFirestoreDataSource {

    suspend fun fetch(
        options: BundleQueryOptions = BundleQueryOptions()
    ): Result<List<MarketBundleDto>>

}