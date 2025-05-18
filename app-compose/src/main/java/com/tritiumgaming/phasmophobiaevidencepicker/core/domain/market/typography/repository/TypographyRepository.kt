package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.TypographyEntity

interface TypographyRepository {

    suspend fun getLocalTypographies(): List<TypographyEntity>
    suspend fun getRemoteTypographies(): List<TypographyEntity>

}