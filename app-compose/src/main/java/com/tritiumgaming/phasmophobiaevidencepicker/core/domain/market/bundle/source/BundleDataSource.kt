package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto

interface BundleDataSource {

    suspend fun fetchAll(): List<MarketBundleDto>

}