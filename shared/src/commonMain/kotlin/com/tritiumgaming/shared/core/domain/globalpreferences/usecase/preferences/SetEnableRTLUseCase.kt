package com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetEnableRTLUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(enable: Boolean) =
        repository.setEnableRTL(enable)
}