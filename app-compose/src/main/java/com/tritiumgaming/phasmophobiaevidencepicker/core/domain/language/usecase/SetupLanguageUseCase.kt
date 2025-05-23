package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageDatastoreRepositoryImpl

class SetupLanguageUseCase(
    private val datastoreRepository: LanguageDatastoreRepositoryImpl
) {
    operator fun invoke() = datastoreRepository.initialSetupEvent()
}
