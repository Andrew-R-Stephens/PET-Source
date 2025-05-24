package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository

class SetupPaletteUseCase(
    private val repository: PaletteRepository
) {
    operator fun invoke() = repository.initialSetupEvent()
}
