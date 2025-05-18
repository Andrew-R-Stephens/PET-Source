package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.NetworkBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource


class BundleLocalDataSource: BundleDataSource {
    override suspend fun fetchAllBundles(): List<NetworkBundleEntity> {
        TODO("Not yet implemented")
    }
}