package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography

interface TypographyRepository {

    suspend fun getLocalTypographies(): List<MarketTypographyDto>
    suspend fun getRemoteTypographies(): List<MarketTypographyDto>

}