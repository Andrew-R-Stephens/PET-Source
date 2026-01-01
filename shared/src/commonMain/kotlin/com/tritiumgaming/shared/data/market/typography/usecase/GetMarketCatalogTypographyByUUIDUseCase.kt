package com.tritiumgaming.shared.data.market.typography.usecase

import com.tritiumgaming.shared.data.market.typography.model.MarketTypography
import com.tritiumgaming.shared.data.market.typography.model.TypographyResources
import com.tritiumgaming.shared.data.market.typography.repository.MarketCatalogTypographyRepository

class GetMarketCatalogTypographyByUUIDUseCase(
    private val repository: MarketCatalogTypographyRepository
) {

    operator fun invoke(
        uuid: String
    ): Result<TypographyResources.TypographyType> {

        val typographyCache = repository.get().getOrDefault(emptyList())
        val cachedTypography: MarketTypography = typographyCache.find { it.uuid == uuid } ?:
            return Result.failure(Exception("MarketTypography not found"))

        val typography = cachedTypography.typography ?:
            return Result.failure(Exception("Typography Type not found in Market Typography"))

        return Result.success(typography)

    }

}