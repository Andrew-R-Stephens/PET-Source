package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette

interface PaletteRepository {

    suspend fun getLocalPalettes(): List<MarketPalette>
    suspend fun getRemotePalettes(): List<MarketPalette>

}