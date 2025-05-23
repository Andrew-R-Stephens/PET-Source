package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyDatastoreRepository

class SetupTypographyUseCase(
    private val datastoreRepository: TypographyDatastoreRepository
) {
    operator fun invoke() = datastoreRepository.initialSetupEvent()
}
