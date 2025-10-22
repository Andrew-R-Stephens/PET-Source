package com.tritiumgaming.shared.core.domain.market.palette.usecase

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SaveCurrentPaletteUseCase(
    private val repository: GlobalPreferencesRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(uuid: String) = repository.savePalette(uuid)

}