package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository

class GetAvailableLanguagesUseCase(
    private val repository: LanguageRepository
) {

    operator fun invoke(): List<LanguageEntity> = repository.getAvailableLanguages()

}