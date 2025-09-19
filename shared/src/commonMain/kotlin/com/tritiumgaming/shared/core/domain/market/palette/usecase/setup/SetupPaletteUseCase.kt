package com.tritiumgaming.shared.core.domain.market.palette.usecase.setup

import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository

class SetupPaletteUseCase(
    private val repository: MarketPaletteRepository
) {
    operator fun invoke() = repository.initializeDatastoreLiveData()
}
