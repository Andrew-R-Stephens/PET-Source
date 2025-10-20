package com.tritiumgaming.shared.core.domain.market.typography.usecase.setup

import com.tritiumgaming.shared.core.domain.market.typography.repository.MarketTypographyRepository

class InitFlowTypographyUseCase(
    private val repository: MarketTypographyRepository
) {
    operator fun invoke() =
        repository.initDatastoreFlow()
}
