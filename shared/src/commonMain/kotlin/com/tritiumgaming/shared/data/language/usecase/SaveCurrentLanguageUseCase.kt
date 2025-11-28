package com.tritiumgaming.shared.data.language.usecase

class SaveCurrentLanguageUseCase(
    private val repository: com.tritiumgaming.shared.data.language.repository.LanguageRepository
) {
    @Suppress("unused")
    suspend operator fun invoke(languageCode: String) =
        repository.saveCurrentLanguageCode(languageCode)
}