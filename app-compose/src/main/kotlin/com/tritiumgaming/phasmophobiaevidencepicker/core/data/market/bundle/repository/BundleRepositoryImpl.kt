package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository.BundleRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource

class BundleRepositoryImpl(
    override val remoteBundleSource: BundleDataSource
): BundleRepository {

    override suspend fun getRemoteBundles(): List<MarketBundleDto> =
        remoteBundleSource.fetchAll()

}