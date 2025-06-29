package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository

class SaveCurrentLanguageUseCase(
    private val repository: LanguageRepository
) {
    @Suppress("unused")
    suspend operator fun invoke(languageCode: String) =
        repository.saveCurrentLanguageCode(languageCode)
}