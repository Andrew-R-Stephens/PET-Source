package com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetMaxHuntWarnFlashTimeUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(time: Long) {
        repository.setMaxHuntWarnFlashTime(time)
    }
}