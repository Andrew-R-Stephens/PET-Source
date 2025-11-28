package com.tritiumgaming.shared.data.preferences.usecase.preferences

class GetMaxHuntWarnFlashTimeUseCase(
    private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {
    operator fun invoke() {
        repository.getMaxHuntWarnFlashTime()
    }
}