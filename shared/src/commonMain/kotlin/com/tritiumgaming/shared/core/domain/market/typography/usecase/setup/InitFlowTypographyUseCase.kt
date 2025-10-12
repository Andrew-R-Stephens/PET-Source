package com.tritiumgaming.shared.core.domain.market.typography.usecase.setup

import com.tritiumgaming.shared.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.shared.core.domain.market.typography.source.TypographyDatastore.TypographyPreferences

class InitFlowTypographyUseCase(
    private val repository: MarketTypographyRepository
) {
    operator fun invoke(onUpdate: (TypographyPreferences) -> Unit = {}) =
        repository.initDatastoreFlow(onUpdate)
}
