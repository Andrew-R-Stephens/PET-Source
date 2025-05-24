package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.source.GlobalPreferencesDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowGlobalPreferencesUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(): Flow<GlobalPreferencesDatastore.GlobalPreferences> =
        repository.initFlow()
}