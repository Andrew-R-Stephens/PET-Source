package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences

 import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class SetDisableScreenSaverUseCase(
     private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(disable: Boolean) =
        repository.setDisableScreenSaver(disable)
}