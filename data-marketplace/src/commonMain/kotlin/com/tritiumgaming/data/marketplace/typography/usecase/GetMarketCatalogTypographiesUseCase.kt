package com.tritiumgaming.data.marketplace.typography.usecase

import com.tritiumgaming.data.marketplace.typography.model.MarketTypography
import com.tritiumgaming.data.marketplace.typography.repository.MarketCatalogTypographyRepository

class GetMarketCatalogTypographiesUseCase(
    private val repository: MarketCatalogTypographyRepository
) {

    operator fun invoke(): Result<List<MarketTypography>> {
        return try {
            Result.success(repository.get().getOrThrow() ) }
        catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Failed to synchronize MarketPalette Catalog cache"))
        }
    }

}