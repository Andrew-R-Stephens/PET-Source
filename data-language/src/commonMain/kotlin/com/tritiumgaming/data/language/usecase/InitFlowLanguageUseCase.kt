package com.tritiumgaming.data.language.usecase

import com.tritiumgaming.data.language.repository.LanguageRepository

class InitFlowLanguageUseCase(
    private val repository: LanguageRepository
) {
    operator fun invoke() = repository.initDatastoreFlow()
}
