package com.tritiumgaming.shared.data.language.usecase

import com.tritiumgaming.shared.data.language.repository.LanguageRepository

class InitFlowLanguageUseCase(
    private val repository: LanguageRepository
) {
    operator fun invoke() = repository.initDatastoreFlow()
}
