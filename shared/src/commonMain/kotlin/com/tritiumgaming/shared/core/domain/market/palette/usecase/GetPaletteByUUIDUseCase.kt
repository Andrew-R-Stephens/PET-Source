package com.tritiumgaming.shared.core.domain.market.palette.usecase

import com.tritiumgaming.shared.core.domain.market.palette.model.PaletteResources.PaletteType
import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository

class GetPaletteByUUIDUseCase(
    private val repository: MarketPaletteRepository
) {

    operator fun invoke(
        uuid: String, defaultPalette: PaletteType
    ): PaletteType {
        val palettesCache = repository.get().getOrDefault(emptyList())
        val cachedPalette = palettesCache.find { it.uuid == uuid }

        return cachedPalette?.palette ?: defaultPalette
    }


}