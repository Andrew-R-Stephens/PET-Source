package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.NetworkBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository.BundleRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource

class BundleRepositoryImpl(
    override val remoteBundleSource: BundleDataSource
): BundleRepository {

    override suspend fun getRemoteBundles(): List<NetworkBundleEntity> =
        remoteBundleSource.fetchAll()

}