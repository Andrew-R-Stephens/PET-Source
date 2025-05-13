package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.api.network.StoreThemesApi
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.model.market.bundle.MarketBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.model.market.palette.PaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.model.market.typography.TypographyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarketRemoteDataSource: MarketRemoteDataSourceInterface {

    override suspend fun fetchAllPalettes(): List<PaletteEntity> = withContext(Dispatchers.IO) {
        StoreThemesApi().fetchAllPalettes()
    }

    override suspend fun fetchAllBundles(): List<MarketBundleEntity> = withContext(Dispatchers.IO) {
        StoreThemesApi().fetchAllBundles()
    }

    override suspend fun fetchAllTypographies(): List<TypographyEntity> = withContext(Dispatchers.IO) {
        StoreThemesApi().fetchAllTypographies()
    }

}

interface MarketRemoteDataSourceInterface {
    suspend fun fetchAllPalettes(): List<PaletteEntity>
    suspend fun fetchAllBundles(): List<MarketBundleEntity>
    suspend fun fetchAllTypographies(): List<TypographyEntity>
}