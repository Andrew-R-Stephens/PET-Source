package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetupGlobalPreferencesUseCase(
    private val repository: GlobalPreferencesRepository
) {
    @Suppress("unused")
    operator fun invoke() = repository.initialSetupEvent()
}