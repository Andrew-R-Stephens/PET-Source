package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteDatastoreRepository

class SetupPaletteUseCase(
    private val datastoreRepository: PaletteDatastoreRepository
) {
    operator fun invoke() = datastoreRepository.initialSetupEvent()
}
