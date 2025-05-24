package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowTypographyUseCase(
    private val datastoreRepository: TypographyDatastoreRepository
) {
    suspend operator fun invoke(): Flow<TypographyDatastore.TypographyPreferences> =
        datastoreRepository.initFlow()
}
