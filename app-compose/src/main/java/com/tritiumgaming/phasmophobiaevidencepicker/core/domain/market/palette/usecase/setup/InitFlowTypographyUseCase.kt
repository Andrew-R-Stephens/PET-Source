package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowPaletteUseCase(
    private val datastoreRepository: PaletteDatastoreRepository
) {
    suspend operator fun invoke(): Flow<PaletteDatastore.PalettePreferences> =
        datastoreRepository.initFlow()
}
