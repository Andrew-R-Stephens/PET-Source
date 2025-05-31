package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource

interface BundleRepository {
    val remoteBundleSource: BundleDataSource

    suspend fun getRemoteBundles(): List<MarketBundleDto>

}