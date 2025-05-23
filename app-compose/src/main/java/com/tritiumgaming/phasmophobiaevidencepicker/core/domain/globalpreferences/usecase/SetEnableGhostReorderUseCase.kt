package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesDatastoreRepository

class SetEnableGhostReorderUseCase(
        private val datastoreRepository: GlobalPreferencesDatastoreRepository
    ) {
        suspend operator fun invoke(enable: Boolean) =
            datastoreRepository.setEnableGhostReorder(enable)
    }