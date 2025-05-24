package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository

class SaveCurrentPaletteUseCase(
    private val repository: PaletteRepository
) {

    suspend operator fun invoke(uuid: String) = repository.saveCurrentPalette(uuid)

}