package com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetEnableGhostReorderUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(enable: Boolean) =
        repository.setEnableGhostReorder(enable)
}