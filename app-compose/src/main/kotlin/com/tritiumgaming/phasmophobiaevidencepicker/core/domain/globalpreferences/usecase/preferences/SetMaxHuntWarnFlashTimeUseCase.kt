package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetMaxHuntWarnFlashTimeUseCase(
    private val repository: GlobalPreferencesRepository
    ) {
    @Suppress("unused")
        suspend operator fun invoke(time: Long) {
            repository.setMaxHuntWarnFlashTime(time)
        }
    }