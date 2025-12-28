package com.tritiumgaming.shared.data.market.typography.usecase

import com.tritiumgaming.shared.data.market.typography.model.MarketTypography
import com.tritiumgaming.shared.data.market.typography.repository.MarketCatalogTypographyRepository

class GetMarketCatalogTypographiesUseCase(
    private val repository: MarketCatalogTypographyRepository
) {

    suspend operator fun invoke(): List<MarketTypography> {
        return repository.synchronizeCache().getOrDefault(emptyList())
    }

}