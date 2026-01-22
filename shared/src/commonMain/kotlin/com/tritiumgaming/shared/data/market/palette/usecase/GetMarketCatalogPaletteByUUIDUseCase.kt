package com.tritiumgaming.shared.data.market.palette.usecase

import com.tritiumgaming.shared.data.market.palette.model.PaletteResources
import com.tritiumgaming.shared.data.market.palette.repository.MarketCatalogPaletteRepository

class GetMarketCatalogPaletteByUUIDUseCase(
    private val repository: MarketCatalogPaletteRepository
) {

    operator fun invoke(
        uuid: String
    ): Result<PaletteResources.PaletteType> {
        val result = repository.get().getOrThrow()

        val cachedPalette = result.firstOrNull { it.uuid == uuid } ?:
            return Result.failure(Exception("Palette with uuid $uuid not found"))

        cachedPalette.palette ?:
            return Result.failure(Exception("Market Palette does not have a palette"))

        return Result.success(cachedPalette.palette)
    }


}