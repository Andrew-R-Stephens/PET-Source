package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageDatastoreRepositoryImpl

class LoadCurrentLanguageUseCase(
    private val datastoreRepository: LanguageDatastoreRepositoryImpl
) {
    suspend operator fun invoke() = datastoreRepository.loadCurrentLanguageCode()
}