package com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetAllowHuntWarnAudioUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(allow: Boolean) =
        repository.setAllowHuntWarnAudio(allow)
}