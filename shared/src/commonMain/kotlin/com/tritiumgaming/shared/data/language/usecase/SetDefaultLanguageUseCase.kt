package com.tritiumgaming.shared.data.language.usecase

class SetDefaultLanguageUseCase(
    val repository: com.tritiumgaming.shared.data.language.repository.LanguageRepository
) {

    operator fun invoke(
        localeLanguage: String,
        languages: List<com.tritiumgaming.shared.data.language.model.LanguageEntity>
    ): Result<com.tritiumgaming.shared.data.language.model.LanguageEntity> {
        // OVERRIDE DEFAULT LANGUAGE
        languages.find { language ->
            language.code
                .equals(localeLanguage, ignoreCase = true)
        }?.let { languageEntity ->
            repository.setDefaultLanguage(languageEntity)

            return Result.success(languageEntity)
        }

        return Result.failure(Exception("Locale language is not supported."))
    }

}