package com.tritiumgaming.shared.core.domain.market.palette.usecase.setup

import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.shared.core.domain.market.palette.source.PaletteDatastore

class InitFlowPaletteUseCase(
    private val repository: MarketPaletteRepository
) {
    suspend operator fun invoke(onUpdate: (PaletteDatastore.PalettePreferences) -> Unit) =
        repository.initDatastoreFlow(onUpdate)
}
