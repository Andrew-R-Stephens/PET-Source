package com.tritiumgaming.data.usecase

import com.tritiumgaming.data.repository.GlobalPreferencesRepository

class SetMaxHuntWarnFlashTimeUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(time: Long) {
        repository.setMaxHuntWarnFlashTime(time)
    }
}