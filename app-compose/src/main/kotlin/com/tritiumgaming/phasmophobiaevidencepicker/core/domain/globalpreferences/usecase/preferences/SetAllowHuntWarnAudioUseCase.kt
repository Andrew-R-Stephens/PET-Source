package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetAllowHuntWarnAudioUseCase(
    private val repository: GlobalPreferencesRepository
) {
    @Suppress("unused")
    suspend operator fun invoke(allow: Boolean) =
        repository.setAllowHuntWarnAudio(allow)
}