package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.MarketPaletteRepository

class SaveCurrentPaletteUseCase(
    private val repository: MarketPaletteRepository
) {

    suspend operator fun invoke(uuid: String) = repository.saveCurrentPalette(uuid)

}