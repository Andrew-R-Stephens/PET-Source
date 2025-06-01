package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.MarketBundleFirestoreDataSource.BundleQueryOptions

interface MarketBundleRemoteRepository {
    
    suspend fun fetchRemote(bundleQueryOptions: BundleQueryOptions): List<MarketBundleDto>

}