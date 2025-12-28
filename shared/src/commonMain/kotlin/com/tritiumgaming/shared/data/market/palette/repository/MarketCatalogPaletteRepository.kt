package com.tritiumgaming.shared.data.market.palette.repository

import com.tritiumgaming.shared.data.market.common.repository.MarketCatalogRepository
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette

interface MarketCatalogPaletteRepository:
    MarketCatalogRepository<MarketPalette>/*, DatastoreRepository<PalettePreferences>*/ {

    /*suspend fun saveCurrent(uuid: String)*/

}

