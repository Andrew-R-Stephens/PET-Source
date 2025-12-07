package com.tritiumgaming.shared.data.preferences.usecase

import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository

class InitFlowUserPreferencesUseCase(
    private val repository: GlobalPreferencesRepository
) {
    operator fun invoke() = repository.initDatastoreFlow()
}