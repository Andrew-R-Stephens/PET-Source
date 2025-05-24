package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowTypographyUseCase(
    private val repository: TypographyRepository
) {
    suspend operator fun invoke(): Flow<TypographyDatastore.TypographyPreferences> =
        repository.initFlow()
}
