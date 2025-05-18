package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.network.StoreThemesApi
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaletteRemoteDataSource : PaletteDataSource<List<NetworkPaletteEntity>> {

    override suspend fun fetchAll(): List<NetworkPaletteEntity> =
        withContext(Dispatchers.IO) {
            StoreThemesApi().fetchAllPalettes()
        }

}