package com.tritiumgaming.shared.core.domain.market.bundle.repository

import com.tritiumgaming.shared.core.domain.market.bundle.model.BundleQueryOptions
import com.tritiumgaming.shared.core.domain.market.bundle.model.MarketBundle

interface MarketBundleRemoteRepository {
    
    suspend fun fetchRemote(bundleQueryOptions: BundleQueryOptions): Result<List<MarketBundle>>

}