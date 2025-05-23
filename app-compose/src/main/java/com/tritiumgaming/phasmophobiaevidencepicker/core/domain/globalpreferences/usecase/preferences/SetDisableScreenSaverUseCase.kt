package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences

 import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesDatastoreRepository

 class SetDisableScreenSaverUseCase(
    private val datastoreRepository: GlobalPreferencesDatastoreRepository
) {
    suspend operator fun invoke(disable: Boolean) =
        datastoreRepository.setDisableScreenSaver(disable)
}