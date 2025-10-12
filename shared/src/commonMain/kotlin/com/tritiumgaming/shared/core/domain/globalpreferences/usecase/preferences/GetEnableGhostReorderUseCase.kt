package com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class GetEnableGhostReorderUseCase(
    private val repository: GlobalPreferencesRepository
) {
    operator fun invoke() = repository.getEnableGhostReorder()
}