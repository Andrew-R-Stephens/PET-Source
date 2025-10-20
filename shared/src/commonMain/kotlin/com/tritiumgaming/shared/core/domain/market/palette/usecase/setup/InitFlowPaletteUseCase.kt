package com.tritiumgaming.shared.core.domain.market.palette.usecase.setup

import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.shared.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowPaletteUseCase(
    private val repository: MarketPaletteRepository
) {
    operator fun invoke(
        onUpdate: (PaletteDatastore.PalettePreferences) -> Unit = {}
    ): Flow<PaletteDatastore.PalettePreferences> =
        repository.initDatastoreFlow()
}
