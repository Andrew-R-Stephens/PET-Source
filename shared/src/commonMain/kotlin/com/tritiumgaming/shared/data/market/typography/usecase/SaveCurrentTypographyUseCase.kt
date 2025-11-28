package com.tritiumgaming.shared.data.market.typography.usecase

class SaveCurrentTypographyUseCase(
    private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(uuid: String) = repository.saveTypography(uuid)

}