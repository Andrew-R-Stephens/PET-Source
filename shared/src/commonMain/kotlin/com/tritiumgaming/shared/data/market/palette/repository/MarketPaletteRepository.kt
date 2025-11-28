package com.tritiumgaming.shared.data.market.palette.repository

import com.tritiumgaming.shared.data.market.common.repository.MarketCatalogRepository
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette

interface MarketPaletteRepository:
    MarketCatalogRepository<MarketPalette>/*, DatastoreRepository<PalettePreferences>*/ {

    /*suspend fun saveCurrent(uuid: String)*/

}

/*
interface MarketPaletteRepository:
    MarketCatalogRepository<MarketPalette>, DatastoreRepository<PalettePreferences> {

    suspend fun saveCurrent(uuid: String)

}*/
