package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.source.GlobalPreferencesDatastore

class InitFlowGlobalPreferencesUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(
        onUpdate: (preferences: GlobalPreferencesDatastore.GlobalPreferences) -> Unit
    ) = repository.initFlow(onUpdate)
}