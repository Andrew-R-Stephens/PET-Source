package com.tritiumgaming.shared.data.preferences.usecase.preferences

class SetAllowHuntWarnAudioUseCase(
    private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {
    suspend operator fun invoke(allow: Boolean) =
        repository.setAllowHuntWarnAudio(allow)
}