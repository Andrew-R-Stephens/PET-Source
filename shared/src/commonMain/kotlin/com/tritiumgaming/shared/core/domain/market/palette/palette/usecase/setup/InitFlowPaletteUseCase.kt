package com.tritiumgaming.shared.core.domain.market.palette.palette.usecase.setup

import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.shared.core.domain.market.palette.source.PaletteDatastore.PalettePreferences

class InitFlowPaletteUseCase(
    private val repository: MarketPaletteRepository
) {
    suspend operator fun invoke(onUpdate: (PalettePreferences) -> Unit) =
        repository.initDatastoreFlow(onUpdate)
}
