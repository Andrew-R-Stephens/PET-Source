package com.tritiumgaming.shared.core.domain.market.typography.usecase

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SaveCurrentTypographyUseCase(
    private val repository: GlobalPreferencesRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(uuid: String) = repository.saveTypography(uuid)

}