package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.MarketTypographyFirestoreDataSource.TypographyQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore
import kotlinx.coroutines.flow.Flow

interface MarketTypographyRepository {

    fun getLocalTypographies(): Result<List<MarketTypographyDto>>
    suspend fun fetchRemoteTypographies(
        typographyQueryOptions: TypographyQueryOptions = TypographyQueryOptions()
    ): Result<List<MarketTypographyDto>>

    suspend fun synchronizeTypographies(): Result<List<MarketTypography>>
    fun getTypographies(): Result<List<MarketTypography>>

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<MarketTypographyDatastore.TypographyPreferences>

    suspend fun saveCurrentTypography(uuid: String)

}