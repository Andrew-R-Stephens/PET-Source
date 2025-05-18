package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteEntity

interface PaletteRepository {

    suspend fun getLocalPalettes(): List<PaletteEntity>
    suspend fun getRemotePalettes(): List<PaletteEntity>

}