package com.tritiumgaming.shared.data.market.typography.usecase

import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository

class SaveCurrentTypographyUseCase(
    private val repository: GlobalPreferencesRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(uuid: String) = repository.saveTypography(uuid)

}