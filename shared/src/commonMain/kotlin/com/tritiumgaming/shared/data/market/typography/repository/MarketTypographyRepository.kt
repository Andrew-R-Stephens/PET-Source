package com.tritiumgaming.shared.data.market.typography.repository

import com.tritiumgaming.shared.data.market.common.repository.MarketCatalogRepository
import com.tritiumgaming.shared.data.market.typography.model.MarketTypography

interface MarketTypographyRepository:
    MarketCatalogRepository<MarketTypography>

/*interface MarketTypographyRepository:
    MarketCatalogRepository<MarketTypography>, DatastoreRepository<TypographyPreferences> {

    suspend fun saveCurrent(uuid: String)

}*/
