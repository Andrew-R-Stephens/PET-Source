package com.tritiumgaming.shared.data.preferences.usecase.preferences

class SetDisableScreenSaverUseCase(
     private val repository: com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
) {
    suspend operator fun invoke(disable: Boolean) =
        repository.setDisableScreenSaver(disable)
}