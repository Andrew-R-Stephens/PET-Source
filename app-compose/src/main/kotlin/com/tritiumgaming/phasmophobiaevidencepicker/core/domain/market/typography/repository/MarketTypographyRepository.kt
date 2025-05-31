package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore
import kotlinx.coroutines.flow.Flow

interface MarketTypographyRepository {

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<MarketTypographyDatastore.TypographyPreferences>

    suspend fun saveCurrentTypography(uuid: String)

    suspend fun getLocalTypographies(): List<MarketTypographyDto>
    suspend fun getRemoteTypographies(): List<MarketTypographyDto>

}