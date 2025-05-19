package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography

interface TypographyRepository {

    suspend fun getLocalTypographies(): List<MarketTypography>
    suspend fun getRemoteTypographies(): List<MarketTypography>

}