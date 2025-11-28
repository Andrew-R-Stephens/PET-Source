package com.tritiumgaming.shared.data.market.bundle.repository

import com.tritiumgaming.shared.data.market.bundle.model.BundleQueryOptions
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle

interface MarketBundleRemoteRepository {
    
    suspend fun fetchRemote(bundleQueryOptions: BundleQueryOptions): Result<List<MarketBundle>>

}