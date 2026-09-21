package com.tritiumgaming.shared.data.market.typography.usecase

import com.tritiumgaming.shared.data.market.typography.model.MarketTypography
import com.tritiumgaming.shared.data.market.typography.repository.MarketCatalogTypographyRepository

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