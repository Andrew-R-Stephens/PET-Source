package com.tritiumgaming.data.marketplace.typography.usecase

import com.tritiumgaming.data.marketplace.typography.model.MarketTypography
import com.tritiumgaming.data.marketplace.typography.repository.MarketCatalogTypographyRepository
import com.tritiumgaming.data.typography.mappers.TypographyResources

class GetMarketCatalogTypographyByUUIDUseCase(
    private val repository: MarketCatalogTypographyRepository
) {

    operator fun invoke(
        uuid: String
    ): Result<TypographyResources.TypographyType> {
        val typographyCache = repository.get().getOrDefault(emptyList())
        val cachedTypography: MarketTypography = typographyCache.find { it.uuid == uuid } ?:
            return Result.failure(Exception("MarketTypography with uuid $uuid not found"))

        val typography = cachedTypography.typography ?:
            return Result.failure(Exception("Market Typography with uuid $uuid does not exist"))

        return Result.success(typography)

    }

}