package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyDatastoreRepository

class SaveCurrentTypographyUseCase(
    private val datastoreRepository: TypographyDatastoreRepository
) {

    suspend operator fun invoke(uuid: String) = datastoreRepository.saveCurrentTypography(uuid)

}