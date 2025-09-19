package com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetupGlobalPreferencesUseCase(
    private val repository: GlobalPreferencesRepository
) {
    operator fun invoke() = repository.initializeDatastoreLiveData()
}