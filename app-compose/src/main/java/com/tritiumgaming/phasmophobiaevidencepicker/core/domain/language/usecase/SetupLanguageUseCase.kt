package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageDatastoreRepository

class SetupLanguageUseCase(
    private val datastoreRepository: LanguageDatastoreRepository
) {
    operator fun invoke() = datastoreRepository.initialSetupEvent()
}
