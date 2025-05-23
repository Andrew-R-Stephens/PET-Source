package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageDatastoreRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageDatastoreRepository

class SetCurrentLanguageUseCase(
    private val datastoreRepository: LanguageDatastoreRepository
) {
    suspend operator fun invoke(languageCode: String) =
        datastoreRepository.saveCurrentLanguageCode(languageCode)
}