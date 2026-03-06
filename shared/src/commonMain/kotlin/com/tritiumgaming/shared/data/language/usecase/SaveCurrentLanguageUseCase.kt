package com.tritiumgaming.shared.data.language.usecase

import com.tritiumgaming.shared.data.language.repository.LanguageRepository

class SaveCurrentLanguageUseCase(
    private val repository: LanguageRepository
) {
    @Suppress("unused")
    suspend operator fun invoke(languageCode: String) =
        repository.saveCurrentLanguageCode(languageCode)
}