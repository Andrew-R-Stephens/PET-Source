package com.tritiumgaming.shared.core.domain.language.usecase

import com.tritiumgaming.shared.core.domain.language.repository.LanguageRepository

class SetupLanguageUseCase(
    private val repository: LanguageRepository
) {
    @Suppress("unused")
    operator fun invoke() = repository.initializeDatastoreLiveData()
}
