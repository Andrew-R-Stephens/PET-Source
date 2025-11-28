package com.tritiumgaming.shared.data.language.usecase

class InitFlowLanguageUseCase(
    private val repository: com.tritiumgaming.shared.data.language.repository.LanguageRepository
) {
    operator fun invoke(onUpdate: (com.tritiumgaming.shared.data.language.source.LanguageDatastore.LanguagePreferences) -> Unit = {}) =
        repository.initDatastoreFlow()
}
