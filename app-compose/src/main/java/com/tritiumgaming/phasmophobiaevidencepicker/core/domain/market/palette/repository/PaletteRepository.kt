package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto

interface PaletteRepository {

    suspend fun getLocalPalettes(): List<MarketPaletteDto>
    suspend fun getRemotePalettes(): List<MarketPaletteDto>

}