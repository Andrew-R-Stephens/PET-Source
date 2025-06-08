package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.MarketPaletteFirestoreDataSource.PaletteQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.flow.Flow

interface MarketPaletteRepository {

    fun getLocalPalettes(): Result<List<MarketPaletteDto>>
    suspend fun fetchRemotePalettes(
        paletteQueryOptions: PaletteQueryOptions = PaletteQueryOptions()
    ): Result<List<MarketPaletteDto>>
    suspend fun synchronizePalettes(): Result<List<MarketPalette>>
    fun getPalettes(): Result<List<MarketPalette>>

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<PaletteDatastore.PalettePreferences>

    suspend fun saveCurrentPalette(uuid: String)

}