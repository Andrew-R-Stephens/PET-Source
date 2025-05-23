package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesDatastoreRepository

class SetupGlobalPreferencesUseCase(
    private val datastoreRepository: GlobalPreferencesDatastoreRepository
) {
    operator fun invoke() = datastoreRepository.initialSetupEvent()
}