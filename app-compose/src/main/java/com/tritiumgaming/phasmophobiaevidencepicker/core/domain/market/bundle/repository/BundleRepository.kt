package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.NetworkBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource

interface BundleRepository {
    val remoteBundleSource: BundleDataSource

    suspend fun getRemoteBundles(): List<NetworkBundleEntity>

}