package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.MarketPaletteFirestoreDataSource.PaletteQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.common.repository.MarketCatalogRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.flow.Flow

interface MarketPaletteRepository:
    MarketCatalogRepository<MarketPalette> {

    fun getLocal(): Result<List<MarketPaletteDto>>
    suspend fun fetchRemote(
        paletteQueryOptions: PaletteQueryOptions = PaletteQueryOptions()
    ): Result<List<MarketPaletteDto>>

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<PaletteDatastore.PalettePreferences>

    suspend fun saveCurrent(uuid: String)

}