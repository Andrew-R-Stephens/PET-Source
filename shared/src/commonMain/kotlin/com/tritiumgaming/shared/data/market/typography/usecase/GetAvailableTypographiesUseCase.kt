package com.tritiumgaming.shared.data.market.typography.usecase

class GetAvailableTypographiesUseCase(
    private val repository: com.tritiumgaming.shared.data.market.typography.repository.MarketTypographyRepository
) {

    suspend operator fun invoke(): List<com.tritiumgaming.shared.data.market.typography.model.MarketTypography> {
        return repository.synchronizeCache().getOrDefault(emptyList())
    }

}