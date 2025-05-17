package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.api.network.StoreThemesApi
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.core.model.MarketBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.core.source.MarketDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.TypographyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarketRemoteDataSource: MarketDataSource {

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
