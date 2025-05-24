package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetAllowCellularDataUseCase(
    private val repository: GlobalPreferencesRepository
    ) {
        suspend operator fun invoke(allow: Boolean) =
            repository.setAllowCellularData(allow)
    }