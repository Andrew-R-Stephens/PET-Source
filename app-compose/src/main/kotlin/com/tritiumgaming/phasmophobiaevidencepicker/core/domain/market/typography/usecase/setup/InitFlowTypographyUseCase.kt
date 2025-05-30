package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowTypographyUseCase(
    private val repository: MarketTypographyRepository
) {
    suspend operator fun invoke(): Flow<MarketTypographyDatastore.TypographyPreferences> =
        repository.initFlow()
}
