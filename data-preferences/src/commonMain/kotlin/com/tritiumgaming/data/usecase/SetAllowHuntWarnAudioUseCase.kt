package com.tritiumgaming.data.usecase

import com.tritiumgaming.data.repository.GlobalPreferencesRepository

class SetAllowHuntWarnAudioUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(allow: Boolean) =
        repository.setAllowHuntWarnAudio(allow)
}