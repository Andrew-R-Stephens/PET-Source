package com.tritiumgaming.shared.data.market.palette.usecase

import com.tritiumgaming.shared.data.market.palette.model.MarketPalette

class GetMarketCatalogPalettesUseCase(
    private val repository: com.tritiumgaming.shared.data.market.palette.repository.MarketCatalogPaletteRepository
) {

    suspend operator fun invoke(): Result<List<MarketPalette>> {
        return try {
            Result.success(repository.get().getOrThrow() ) }
        catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Failed to synchronize MarketPalette Catalog cache"))
        }
    }

}