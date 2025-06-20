package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.datastore.DatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.MarketTypographyFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.common.repository.MarketCatalogRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore.TypographyPreferences

interface MarketTypographyRepository:
    MarketCatalogRepository<MarketTypography>, DatastoreRepository<TypographyPreferences> {

    fun getLocal(): Result<List<MarketTypographyDto>>

    suspend fun fetchRemote(
        queryOptions: MarketTypographyFirestoreDataSource.TypographyQueryOptions =
            MarketTypographyFirestoreDataSource.TypographyQueryOptions()
    ): Result<List<MarketTypographyDto>>

    suspend fun saveCurrent(uuid: String)

}
