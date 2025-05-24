package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository

class LoadCurrentLanguageUseCase(
    private val repository: LanguageRepository
) {
    suspend operator fun invoke() = repository.loadCurrentLanguageCode()
}