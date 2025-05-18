package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.NetworkBundleEntity

interface BundleDataSource {

    suspend fun fetchAllBundles(): List<NetworkBundleEntity>

}