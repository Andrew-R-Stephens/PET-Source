package com.tritiumgaming.shared.data.language.usecase

import com.tritiumgaming.shared.data.language.model.LanguageEntity
import com.tritiumgaming.shared.data.language.repository.LanguageRepository

class SetDefaultLanguageUseCase(
    val repository: LanguageRepository
) {

    operator fun invoke(
        localeLanguage: String,
        languages: List<LanguageEntity>
    ): Result<LanguageEntity> {
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