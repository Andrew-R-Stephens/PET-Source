package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.palette.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.MarketPaletteRepository

class SetupPaletteUseCase(
    private val repository: MarketPaletteRepository
) {
    operator fun invoke() = repository.initialSetupEvent()
}
