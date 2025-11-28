package com.tritiumgaming.shared.data.language.usecase

class SetupLanguageUseCase(
    private val repository: com.tritiumgaming.shared.data.language.repository.LanguageRepository
) {
    operator fun invoke() = repository.initializeDatastoreLiveData()
}
