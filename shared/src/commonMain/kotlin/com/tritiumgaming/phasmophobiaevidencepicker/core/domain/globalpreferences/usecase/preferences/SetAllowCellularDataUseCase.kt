package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetAllowCellularDataUseCase(
    private val repository: GlobalPreferencesRepository
) {
    @Suppress("unused")
    suspend operator fun invoke(allow: Boolean) =
        repository.setAllowCellularData(allow)
}