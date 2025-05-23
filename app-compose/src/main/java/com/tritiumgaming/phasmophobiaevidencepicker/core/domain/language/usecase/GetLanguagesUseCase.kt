package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository

class GetLanguagesUseCase(
   private val languageRepository: LanguageRepository
) {

    operator fun invoke(): List<LanguageEntity> = languageRepository.getLanguages()

}