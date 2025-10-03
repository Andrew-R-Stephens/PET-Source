package com.tritiumgaming.shared.core.domain.language.usecase

import androidx.compose.ui.text.intl.Locale
import com.tritiumgaming.shared.core.domain.language.model.LanguageEntity
import com.tritiumgaming.shared.core.domain.language.repository.LanguageRepository

class SetDefaultLanguageUseCase() {

    operator fun invoke(
        localeLanguage: String,
        languages: List<LanguageEntity>
    ): Result<LanguageEntity> {
        // OVERRIDE DEFAULT LANGUAGE
        languages.find { language ->
            language.code
                .equals(localeLanguage, ignoreCase = true)
        }?.let { languageEntity ->
            return Result.success(languageEntity)
        }

        return Result.failure(Exception("Locale language is not supported."))
    }

}