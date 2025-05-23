package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowLanguageUseCase(
    private val datastoreRepository: LanguageDatastoreRepository
) {
    suspend operator fun invoke(): Flow<LanguageDatastore.LanguagePreferences> =
        datastoreRepository.initFlow()
}
