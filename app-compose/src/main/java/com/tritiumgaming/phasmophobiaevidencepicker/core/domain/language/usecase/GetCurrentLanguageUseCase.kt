package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageDatastoreRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageDatastoreRepository

class GetCurrentLanguageUseCase(
    private val datastoreRepository: LanguageDatastoreRepository
) {
    operator fun invoke() = datastoreRepository.getCurrentLanguageCode()
}