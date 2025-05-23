package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesDatastoreRepository

class SetAllowIntroductionUseCase(
        private val datastoreRepository: GlobalPreferencesDatastoreRepository
    ) {
        suspend operator fun invoke(allow: Boolean) =
            datastoreRepository.setAllowIntroduction(allow)
    }