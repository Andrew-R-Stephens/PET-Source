package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageDatastoreRepositoryImpl

class SetCurrentLanguageUseCase(
    private val datastoreRepository: LanguageDatastoreRepositoryImpl
) {
    suspend operator fun invoke(languageCode: String) =
        datastoreRepository.saveCurrentLanguageCode(languageCode)
}