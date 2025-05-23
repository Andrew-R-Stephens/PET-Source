package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesDatastoreRepository

class SetMaxHuntWarnFlashTimeUseCase(
        private val datastoreRepository: GlobalPreferencesDatastoreRepository
    ) {
        suspend operator fun invoke(time: Long) {
            datastoreRepository.setMaxHuntWarnFlashTime(time)
        }
    }