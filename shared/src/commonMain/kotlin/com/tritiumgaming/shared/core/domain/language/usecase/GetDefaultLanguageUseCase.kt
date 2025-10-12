package com.tritiumgaming.shared.core.domain.language.usecase

import com.tritiumgaming.shared.core.domain.language.model.LanguageEntity
import com.tritiumgaming.shared.core.domain.language.repository.LanguageRepository

class GetDefaultLanguageUseCase(
    private val repository: LanguageRepository
) {

    operator fun invoke(): Result<LanguageEntity> {
        // OVERRIDE DEFAULT LANGUAGE
        Result.success(repository.getDefaultLanguage())

        return Result.failure(Exception("Locale language is not supported."))
    }

}