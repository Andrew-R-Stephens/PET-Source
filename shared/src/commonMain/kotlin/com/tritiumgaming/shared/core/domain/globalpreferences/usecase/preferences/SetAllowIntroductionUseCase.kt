package com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetAllowIntroductionUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(allow: Boolean) =
        repository.setAllowIntroduction(allow)
}