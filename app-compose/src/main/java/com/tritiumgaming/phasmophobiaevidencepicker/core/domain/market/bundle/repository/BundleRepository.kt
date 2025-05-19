package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.NetworkBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource

interface BundleRepository {
    val remoteBundleSource: BundleDataSource

    suspend fun getRemoteBundles(): List<NetworkBundleEntity>

}