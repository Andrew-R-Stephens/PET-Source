package com.tritiumgaming.shared.data.preferences.usecase

import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository

class GetEnableGhostReorderUseCase(
    private val repository: GlobalPreferencesRepository
) {
    operator fun invoke() = repository.getEnableGhostReorder()
}