package com.tritiumgaming.shared.data.language.usecase

class GetAvailableLanguagesUseCase(
    private val repository: com.tritiumgaming.shared.data.language.repository.LanguageRepository
) {

    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.language.model.LanguageEntity>> {

        val result = repository.getAvailableLanguages()

        return result
    }

}