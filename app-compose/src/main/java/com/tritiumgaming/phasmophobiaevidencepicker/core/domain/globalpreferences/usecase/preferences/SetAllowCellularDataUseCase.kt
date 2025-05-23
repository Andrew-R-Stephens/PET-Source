package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesDatastoreRepository

class SetAllowCellularDataUseCase(
        private val datastoreRepository: GlobalPreferencesDatastoreRepository
    ) {
        suspend operator fun invoke(allow: Boolean) =
            datastoreRepository.setAllowCellularData(allow)
    }