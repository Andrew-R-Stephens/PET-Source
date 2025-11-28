package com.tritiumgaming.shared.data.market.palette.usecase

class SaveCurrentPaletteUseCase(
    private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(uuid: String) = repository.savePalette(uuid)

}