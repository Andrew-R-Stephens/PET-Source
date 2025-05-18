package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.network.StoreThemesApi
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.NetworkBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BundleRemoteDataSource: BundleDataSource {

    override suspend fun fetchAllBundles(): List<NetworkBundleEntity> = withContext(Dispatchers.IO) {
        StoreThemesApi().fetchAllBundles()
    }

}