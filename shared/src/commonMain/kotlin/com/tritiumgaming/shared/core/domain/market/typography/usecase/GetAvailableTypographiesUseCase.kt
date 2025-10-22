package com.tritiumgaming.shared.core.domain.market.typography.usecase

import com.tritiumgaming.shared.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.shared.core.domain.market.typography.repository.MarketTypographyRepository

class GetAvailableTypographiesUseCase(
    private val repository: MarketTypographyRepository
) {

    suspend operator fun invoke(): List<MarketTypography> {
        return repository.synchronizeCache().getOrDefault(emptyList())
    }

}