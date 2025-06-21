package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.common.repository.MarketCatalogRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore.PalettePreferences

interface MarketPaletteRepository:
    MarketCatalogRepository<MarketPalette>, DatastoreRepository<PalettePreferences> {

    suspend fun saveCurrent(uuid: String)

}