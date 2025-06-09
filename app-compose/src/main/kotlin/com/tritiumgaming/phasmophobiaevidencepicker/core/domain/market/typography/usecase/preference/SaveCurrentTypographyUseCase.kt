package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository

class SaveCurrentTypographyUseCase(
    private val repository: MarketTypographyRepository
) {

    suspend operator fun invoke(uuid: String) = repository.saveCurrent(uuid)

}