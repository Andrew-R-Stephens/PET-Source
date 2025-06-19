package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.MarketPaletteRepository

class SetupPaletteUseCase(
    private val repository: MarketPaletteRepository
) {
    @Suppress("unused")
    operator fun invoke() = repository.initialSetupEvent()
}
