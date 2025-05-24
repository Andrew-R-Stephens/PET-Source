package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowPaletteUseCase(
    private val repository: PaletteRepository
) {
    suspend operator fun invoke(): Flow<PaletteDatastore.PalettePreferences> =
        repository.initFlow()
}
