package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository

class SaveCurrentTypographyUseCase(
    private val repository: TypographyRepository
) {

    suspend operator fun invoke(uuid: String) = repository.saveCurrentTypography(uuid)

}