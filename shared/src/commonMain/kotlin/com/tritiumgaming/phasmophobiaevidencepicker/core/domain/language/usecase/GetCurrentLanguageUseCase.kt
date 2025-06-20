package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository

class GetCurrentLanguageUseCase(
    private val repository: LanguageRepository
) {
    operator fun invoke() = repository.getCurrentLanguageCode()
}