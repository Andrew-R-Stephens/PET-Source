package com.tritiumgaming.shared.data.market.palette.usecase

class GetPaletteByUUIDUseCase(
    private val repository: com.tritiumgaming.shared.data.market.palette.repository.MarketPaletteRepository
) {

    operator fun invoke(
        uuid: String, defaultPalette: com.tritiumgaming.shared.data.market.palette.model.PaletteResources.PaletteType
    ): com.tritiumgaming.shared.data.market.palette.model.PaletteResources.PaletteType {
        val palettesCache = repository.get().getOrDefault(emptyList())
        val cachedPalette = palettesCache.find { it.uuid == uuid }

        return cachedPalette?.palette ?: defaultPalette
    }


}