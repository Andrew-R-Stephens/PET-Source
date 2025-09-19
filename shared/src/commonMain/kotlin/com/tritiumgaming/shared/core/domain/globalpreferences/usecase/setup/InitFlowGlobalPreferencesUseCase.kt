package com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.shared.core.domain.globalpreferences.source.GlobalPreferencesDatastore

class InitFlowGlobalPreferencesUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(
        onUpdate: (preferences: GlobalPreferencesDatastore.GlobalPreferences) -> Unit
    ) = repository.initDatastoreFlow(onUpdate)
}