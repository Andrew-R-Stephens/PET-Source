package com.tritiumgaming.shared.data.preferences.usecase

import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository

class SaveCurrentTypographyUseCase(
    private val repository: GlobalPreferencesRepository
) {

    suspend operator fun invoke(uuid: String) = repository.saveTypography(uuid)

}