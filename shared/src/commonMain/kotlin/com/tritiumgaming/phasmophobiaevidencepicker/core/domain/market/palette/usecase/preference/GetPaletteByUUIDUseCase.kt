package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.MarketPaletteRepository

class GetPaletteByUUIDUseCase(
    private val repository: MarketPaletteRepository
) {

    operator fun invoke(
        uuid: String, defaultPalette: PaletteType
    ): PaletteType {
        //Log.d("GetPaletteByUUIDUseCase", "Finding cached palette: $uuid")
        val palettesCache = repository.get().getOrDefault(emptyList())
        val cachedPalette = palettesCache.find { it.uuid == uuid }

        //Log.d("GetPaletteByUUIDUseCase", "Cached palette found $cachedPalette")
        //Log.d("GetPaletteByUUIDUseCase", "ExtendedPalette: ${cachedPalette?.palette}")

        return cachedPalette?.palette ?: defaultPalette
    }


}