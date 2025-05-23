package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.source.GlobalPreferencesDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowGlobalPreferencesUseCase(
    private val datastoreRepository: GlobalPreferencesDatastoreRepository
) {
    suspend operator fun invoke(): Flow<GlobalPreferencesDatastore.GlobalPreferences> =
        datastoreRepository.initFlow()
}