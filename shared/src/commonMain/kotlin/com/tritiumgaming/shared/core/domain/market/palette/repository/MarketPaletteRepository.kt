package com.tritiumgaming.shared.core.domain.market.palette.repository

import com.tritiumgaming.shared.core.domain.market.common.repository.MarketCatalogRepository
import com.tritiumgaming.shared.core.domain.market.palette.model.MarketPalette

interface MarketPaletteRepository:
    MarketCatalogRepository<MarketPalette>/*, DatastoreRepository<PalettePreferences>*/ {

    /*suspend fun saveCurrent(uuid: String)*/

}

/*
interface MarketPaletteRepository:
    MarketCatalogRepository<MarketPalette>, DatastoreRepository<PalettePreferences> {

    suspend fun saveCurrent(uuid: String)

}*/
