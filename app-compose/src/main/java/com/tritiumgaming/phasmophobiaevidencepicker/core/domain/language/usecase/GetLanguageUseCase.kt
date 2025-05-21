package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository

class GetLanguageUseCase(
   private val  repository: LanguageRepository
) {

    fun invoke(): List<LanguageEntity> = repository.getLanguages()

}