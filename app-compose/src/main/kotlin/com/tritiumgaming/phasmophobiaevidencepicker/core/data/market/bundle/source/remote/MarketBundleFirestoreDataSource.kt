package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.shared.core.domain.market.bundle.model.BundleQueryOptions

interface MarketBundleFirestoreDataSource {

    suspend fun fetch(
        options: BundleQueryOptions = BundleQueryOptions()
    ): Result<List<MarketBundleDto>>

}