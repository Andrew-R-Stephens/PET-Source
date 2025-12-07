package com.tritiumgaming.shared.data.preferences.usecase

import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository

class SetDisableScreenSaverUseCase(
     private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(disable: Boolean) =
        repository.setDisableScreenSaver(disable)
}