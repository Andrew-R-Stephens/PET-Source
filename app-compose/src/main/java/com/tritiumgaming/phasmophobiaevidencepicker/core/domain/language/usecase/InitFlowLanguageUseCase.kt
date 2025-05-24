package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowLanguageUseCase(
    private val repository: LanguageRepository
) {
    suspend operator fun invoke(): Flow<LanguageDatastore.LanguagePreferences> =
        repository.initFlow()
}
