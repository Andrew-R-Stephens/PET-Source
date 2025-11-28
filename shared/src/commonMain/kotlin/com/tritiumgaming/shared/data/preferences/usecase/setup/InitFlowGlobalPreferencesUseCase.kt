package com.tritiumgaming.shared.data.preferences.usecase.setup

class InitFlowGlobalPreferencesUseCase(
    private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {
    operator fun invoke(
        onUpdate: (preferences: com.tritiumgaming.shared.data.preferences.source.GlobalPreferencesDatastore.GlobalPreferences) -> Unit = {}
    ) = repository.initDatastoreFlow()
}