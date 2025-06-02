package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository

class GetAvailableTypographiesUseCase(
    private val repository: MarketTypographyRepository
) {

    suspend operator fun invoke(): List<MarketTypography> {
        return repository.synchronizeTypographies()
    }

}