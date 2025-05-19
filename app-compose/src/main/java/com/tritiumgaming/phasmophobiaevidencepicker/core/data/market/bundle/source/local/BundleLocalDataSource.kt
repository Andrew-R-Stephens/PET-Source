package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.NetworkBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource

class BundleLocalDataSource: BundleDataSource {
    override suspend fun fetchAll(): List<NetworkBundleEntity> {
        TODO("Not yet implemented")
    }
}