package com.tritiumgaming.shared.core.domain.market.typography.repository

import com.tritiumgaming.shared.core.domain.datastore.DatastoreRepository
import com.tritiumgaming.shared.core.domain.market.common.repository.MarketCatalogRepository
import com.tritiumgaming.shared.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.shared.core.domain.market.typography.source.TypographyDatastore.TypographyPreferences

interface MarketTypographyRepository:
    MarketCatalogRepository<MarketTypography>

/*interface MarketTypographyRepository:
    MarketCatalogRepository<MarketTypography>, DatastoreRepository<TypographyPreferences> {

    suspend fun saveCurrent(uuid: String)

}*/
