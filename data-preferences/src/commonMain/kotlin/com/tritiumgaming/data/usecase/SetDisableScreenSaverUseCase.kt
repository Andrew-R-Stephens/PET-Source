package com.tritiumgaming.data.usecase

import com.tritiumgaming.data.repository.GlobalPreferencesRepository

class SetDisableScreenSaverUseCase(
     private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(disable: Boolean) =
        repository.setDisableScreenSaver(disable)

}