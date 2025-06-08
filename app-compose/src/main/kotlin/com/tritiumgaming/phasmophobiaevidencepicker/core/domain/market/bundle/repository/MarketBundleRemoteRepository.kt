package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.MarketBundleFirestoreDataSourceImpl.BundleQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.MarketBundle

interface MarketBundleRemoteRepository {
    
    suspend fun fetchRemote(bundleQueryOptions: BundleQueryOptions): Result<List<MarketBundle>>

}