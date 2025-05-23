package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesDatastoreRepository

class SetEnableGhostReorderUseCase(
        private val datastoreRepository: GlobalPreferencesDatastoreRepository
    ) {
        suspend operator fun invoke(enable: Boolean) =
            datastoreRepository.setEnableGhostReorder(enable)
    }