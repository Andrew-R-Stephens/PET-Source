package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository

class SetupLanguageUseCase(
    private val repository: LanguageRepository
) {
    @Suppress("unused")
    operator fun invoke() = repository.initialSetupEvent()
}
