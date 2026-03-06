package com.tritiumgaming.shared.data.language.usecase

import com.tritiumgaming.shared.data.language.model.LanguageEntity
import com.tritiumgaming.shared.data.language.repository.LanguageRepository

class GetAvailableLanguagesUseCase(
    private val repository: LanguageRepository
) {

    operator fun invoke(): Result<List<LanguageEntity>> {

        val result = repository.getAvailableLanguages()

        return result
    }

}