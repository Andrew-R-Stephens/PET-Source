package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.BundleQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.MarketBundle

interface MarketBundleRemoteRepository {
    
    suspend fun fetchRemote(bundleQueryOptions: BundleQueryOptions): Result<List<MarketBundle>>

}