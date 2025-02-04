package com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.network

import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketPaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketTypographyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkMarketDataSource {

    suspend fun fetchAllPalettes(): List<MarketPaletteEntity> = withContext(Dispatchers.IO) {
            StoreThemesApi().fetchAllPalettes()
        }

    suspend fun fetchAllBundles(): List<MarketBundleEntity> = withContext(Dispatchers.IO) {
            StoreThemesApi().fetchAllBundles()
        }

    suspend fun fetchAllTypographys(): List<MarketTypographyEntity> = withContext(Dispatchers.IO) {
            StoreThemesApi().fetchAllTypographys()
        }
}
