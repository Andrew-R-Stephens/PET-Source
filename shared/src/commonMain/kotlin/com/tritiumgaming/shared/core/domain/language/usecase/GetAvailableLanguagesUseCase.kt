package com.tritiumgaming.shared.core.domain.language.usecase

import com.tritiumgaming.shared.core.domain.language.model.LanguageEntity
import com.tritiumgaming.shared.core.domain.language.repository.LanguageRepository

class GetAvailableLanguagesUseCase(
    private val repository: LanguageRepository
) {

    operator fun invoke(): Result<List<LanguageEntity>> {

        val result = repository.getAvailableLanguages()

        return result
    }

}