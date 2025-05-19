package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.NetworkBundleEntity

interface BundleDataSource {

    suspend fun fetchAll(): List<NetworkBundleEntity>

}