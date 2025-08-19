package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore.LanguagePreferences

class InitFlowLanguageUseCase(
    private val repository: LanguageRepository
) {
    suspend operator fun invoke(onUpdate: (LanguagePreferences) -> Unit) =
        repository.initDatastoreFlow(onUpdate)
}
