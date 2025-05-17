package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.core.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.core.model.MarketBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.TypographyEntity

interface MarketDataSource {
    suspend fun fetchAllPalettes(): List<PaletteEntity>
    suspend fun fetchAllBundles(): List<MarketBundleEntity>
    suspend fun fetchAllTypographies(): List<TypographyEntity>
}