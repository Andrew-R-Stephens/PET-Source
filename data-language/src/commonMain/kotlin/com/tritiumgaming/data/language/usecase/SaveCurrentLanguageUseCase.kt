package com.tritiumgaming.data.language.usecase

import com.tritiumgaming.data.language.repository.LanguageRepository

class SaveCurrentLanguageUseCase(
    private val repository: LanguageRepository
) {
    @Suppress("unused")
    suspend operator fun invoke(languageCode: String) =
        repository.saveCurrentLanguageCode(languageCode)
}