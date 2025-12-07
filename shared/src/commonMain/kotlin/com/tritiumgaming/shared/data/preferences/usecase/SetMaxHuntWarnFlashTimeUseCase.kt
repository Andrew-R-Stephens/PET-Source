package com.tritiumgaming.shared.data.preferences.usecase

import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository

class SetMaxHuntWarnFlashTimeUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(time: Long) {
        repository.setMaxHuntWarnFlashTime(time)
    }
}