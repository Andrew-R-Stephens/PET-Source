package com.tritiumgaming.shared.data.preferences.usecase.preferences

class SetMaxHuntWarnFlashTimeUseCase(
    private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {
    suspend operator fun invoke(time: Long) {
        repository.setMaxHuntWarnFlashTime(time)
    }
}