package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDatastore
import kotlinx.coroutines.flow.Flow

interface TypographyRepository {

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<TypographyDatastore.TypographyPreferences>

    suspend fun saveCurrentTypography(uuid: String)

    suspend fun getLocalTypographies(): List<MarketTypographyDto>
    suspend fun getRemoteTypographies(): List<MarketTypographyDto>

}