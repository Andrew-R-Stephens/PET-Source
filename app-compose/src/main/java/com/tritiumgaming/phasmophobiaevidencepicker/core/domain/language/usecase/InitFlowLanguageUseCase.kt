package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageDatastoreRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowLanguageUseCase(
    private val datastoreRepository: LanguageDatastoreRepositoryImpl
) {
    suspend operator fun invoke(): Flow<LanguageDatastore.LanguagePreferences> =
        datastoreRepository.initFlow()
}
