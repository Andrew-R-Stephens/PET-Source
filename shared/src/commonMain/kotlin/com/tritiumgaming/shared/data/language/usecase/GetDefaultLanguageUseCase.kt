package com.tritiumgaming.shared.data.language.usecase

class GetDefaultLanguageUseCase(
    private val repository: com.tritiumgaming.shared.data.language.repository.LanguageRepository
) {

    operator fun invoke(): Result<com.tritiumgaming.shared.data.language.model.LanguageEntity> {
        // OVERRIDE DEFAULT LANGUAGE
        Result.success(repository.getDefaultLanguage())

        return Result.failure(Exception("Locale language is not supported."))
    }

}