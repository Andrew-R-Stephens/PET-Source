package com.tritiumgaming.shared.core.domain.market.palette.palette.usecase.preference

import com.tritiumgaming.shared.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository

class GetAvailablePalettesUseCase(
    private val repository: MarketPaletteRepository
) {

    suspend operator fun invoke(): List<MarketPalette> {
        return repository.synchronizeCache().getOrDefault(emptyList())
    }

}