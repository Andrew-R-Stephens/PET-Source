package com.tritiumgaming.data.language.usecase

import com.tritiumgaming.data.language.model.LanguageEntity
import com.tritiumgaming.data.language.repository.LanguageRepository

class GetDefaultLanguageUseCase(
    private val repository: LanguageRepository
) {

    operator fun invoke(): Result<LanguageEntity> {
        return repository.getDefaultLanguage()?.let {
            Result.success(it)
        } ?: Result.failure(Exception("Locale language is not supported."))
    }

}