package com.tritiumgaming.shared.core.domain.market.palette.repository

import com.tritiumgaming.shared.core.domain.datastore.DatastoreRepository
import com.tritiumgaming.shared.core.domain.market.common.repository.MarketCatalogRepository
import com.tritiumgaming.shared.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.shared.core.domain.market.palette.source.PaletteDatastore.PalettePreferences

interface MarketPaletteRepository:
    MarketCatalogRepository<MarketPalette>, DatastoreRepository<PalettePreferences> {

    suspend fun saveCurrent(uuid: String)

}