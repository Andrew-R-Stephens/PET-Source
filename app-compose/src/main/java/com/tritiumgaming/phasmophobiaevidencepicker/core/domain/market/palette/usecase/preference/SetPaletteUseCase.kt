package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteDatastoreRepository

class SetPaletteUseCase(
    private val datastoreRepository: PaletteDatastoreRepository
) {

    suspend operator fun invoke(uuid: String) = datastoreRepository.saveCurrentPalette(uuid)

}