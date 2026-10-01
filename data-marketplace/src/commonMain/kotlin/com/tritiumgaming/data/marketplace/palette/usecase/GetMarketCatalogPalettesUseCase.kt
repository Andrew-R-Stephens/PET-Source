package com.tritiumgaming.data.marketplace.palette.usecase

import com.tritiumgaming.data.marketplace.palette.model.MarketPalette
import com.tritiumgaming.data.marketplace.palette.repository.MarketCatalogPaletteRepository

class GetMarketCatalogPalettesUseCase(
    private val repository: MarketCatalogPaletteRepository
) {

    operator fun invoke(): Result<List<MarketPalette>> {
        return try {
            Result.success(repository.get().getOrThrow() ) }
        catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Failed to synchronize MarketPalette Catalog cache"))
        }
    }

}