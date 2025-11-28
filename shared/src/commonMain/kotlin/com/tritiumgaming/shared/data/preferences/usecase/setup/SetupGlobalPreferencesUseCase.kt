package com.tritiumgaming.shared.data.preferences.usecase.setup

class SetupGlobalPreferencesUseCase(
    private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {
    operator fun invoke() = repository.initializeDatastoreLiveData()
}