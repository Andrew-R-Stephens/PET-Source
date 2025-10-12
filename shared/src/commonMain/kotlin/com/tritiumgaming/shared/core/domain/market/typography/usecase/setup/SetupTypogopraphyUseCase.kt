package com.tritiumgaming.shared.core.domain.market.typography.usecase.setup

import com.tritiumgaming.shared.core.domain.market.typography.repository.MarketTypographyRepository

class InitTypographyDataStoreUseCase(
    private val repository: MarketTypographyRepository
) {
    @Suppress("unused")
    operator fun invoke() = repository.initializeDatastoreLiveData()
}
