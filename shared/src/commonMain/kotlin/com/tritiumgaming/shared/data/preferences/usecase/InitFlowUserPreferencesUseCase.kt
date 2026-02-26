package com.tritiumgaming.shared.data.preferences.usecase

import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.shared.data.preferences.source.GlobalPreferencesDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowUserPreferencesUseCase(
    private val repository: GlobalPreferencesRepository
) {
    operator fun invoke(): Flow<GlobalPreferencesDatastore.GlobalPreferences> =
        repository.initDatastoreFlow()

}