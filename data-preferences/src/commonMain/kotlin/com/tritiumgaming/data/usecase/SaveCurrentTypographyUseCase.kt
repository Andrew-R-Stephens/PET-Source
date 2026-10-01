package com.tritiumgaming.data.usecase

import com.tritiumgaming.data.repository.GlobalPreferencesRepository

class SaveCurrentTypographyUseCase(
    private val repository: GlobalPreferencesRepository
) {

    suspend operator fun invoke(uuid: String) = repository.saveTypography(uuid)

}