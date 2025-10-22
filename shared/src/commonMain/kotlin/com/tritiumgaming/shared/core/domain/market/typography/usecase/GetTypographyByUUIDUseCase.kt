package com.tritiumgaming.shared.core.domain.market.typography.usecase

import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType
import com.tritiumgaming.shared.core.domain.market.typography.repository.MarketTypographyRepository

class GetTypographyByUUIDUseCase(
    private val repository: MarketTypographyRepository
) {

    operator fun invoke(
        uuid: String, defaultTypography: TypographyType
    ): TypographyType {
        val typographyCache = repository.get().getOrDefault(emptyList())
        val cachedTypography = typographyCache.find { it.uuid == uuid }

        return cachedTypography?.typography ?: defaultTypography
    }

}