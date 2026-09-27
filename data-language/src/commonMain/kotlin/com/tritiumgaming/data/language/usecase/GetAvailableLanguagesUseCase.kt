package com.tritiumgaming.data.language.usecase

import com.tritiumgaming.data.language.model.LanguageEntity
import com.tritiumgaming.data.language.repository.LanguageRepository

class GetAvailableLanguagesUseCase(
    private val repository: LanguageRepository
) {

    operator fun invoke(): Result<List<LanguageEntity>> {

        val result = repository.getAvailableLanguages()

        return result
    }

}