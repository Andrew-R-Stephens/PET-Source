package com.tritiumgaming.data.usecase

import com.tritiumgaming.data.repository.GlobalPreferencesRepository

class SetAllowIntroductionUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(allow: Boolean) =
        repository.setAllowIntroduction(allow)
}