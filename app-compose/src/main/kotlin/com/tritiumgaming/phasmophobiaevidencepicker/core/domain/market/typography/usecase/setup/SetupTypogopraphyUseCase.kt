package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository

class SetupTypographyUseCase(
    private val repository: MarketTypographyRepository
) {
    operator fun invoke() = repository.initialSetupEvent()
}
