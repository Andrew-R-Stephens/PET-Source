package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore.TypographyPreferences

class InitFlowTypographyUseCase(
    private val repository: MarketTypographyRepository
) {
    suspend operator fun invoke(onUpdate: (TypographyPreferences) -> Unit) =
        repository.initDatastoreFlow(onUpdate)
}
