package com.tritiumgaming.data.usecase

import com.tritiumgaming.data.repository.GlobalPreferencesRepository
import com.tritiumgaming.data.source.GlobalPreferencesDatastore.GlobalPreferences
import kotlinx.coroutines.flow.Flow

class InitFlowUserPreferencesUseCase(
    private val repository: GlobalPreferencesRepository
) {
    operator fun invoke(): Flow<GlobalPreferences> =
        repository.initDatastoreFlow()

}