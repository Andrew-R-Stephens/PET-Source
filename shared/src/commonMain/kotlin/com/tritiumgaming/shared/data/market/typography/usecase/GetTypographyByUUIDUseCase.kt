package com.tritiumgaming.shared.data.market.typography.usecase

class GetTypographyByUUIDUseCase(
    private val repository: com.tritiumgaming.shared.data.market.typography.repository.MarketTypographyRepository
) {

    operator fun invoke(
        uuid: String, defaultTypography: com.tritiumgaming.shared.data.market.typography.model.TypographyResources.TypographyType
    ): com.tritiumgaming.shared.data.market.typography.model.TypographyResources.TypographyType {
        val typographyCache = repository.get().getOrDefault(emptyList())
        val cachedTypography = typographyCache.find { it.uuid == uuid }

        return cachedTypography?.typography ?: defaultTypography
    }

}