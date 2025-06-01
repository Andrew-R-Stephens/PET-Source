package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.MarketPaletteFirestoreDataSource.PaletteQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.flow.Flow

interface MarketPaletteRepository {

    fun getLocalPalettes(): List<MarketPaletteDto>
    suspend fun fetchRemotePalettes(
        paletteQueryOptions: PaletteQueryOptions = PaletteQueryOptions()
    ): List<MarketPaletteDto>
    suspend fun synchronizePalettes()
    fun getPalettes(): List<MarketPalette>

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<PaletteDatastore.PalettePreferences>

    suspend fun saveCurrentPalette(uuid: String)

}