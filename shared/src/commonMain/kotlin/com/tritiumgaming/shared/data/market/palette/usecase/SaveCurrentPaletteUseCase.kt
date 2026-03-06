package com.tritiumgaming.shared.data.market.palette.usecase

import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository

class SaveCurrentPaletteUseCase(
    private val repository: GlobalPreferencesRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(uuid: String) = repository.savePalette(uuid)

}