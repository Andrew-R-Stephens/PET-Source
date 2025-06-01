package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource

class BundleLocalDataSource: BundleDataSource {

    override suspend fun fetchAll(): List<MarketBundleDto> {
        TODO("Not yet implemented")
    }

}