package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository

class SetupTypographyUseCase(
    private val repository: TypographyRepository
) {
    operator fun invoke() = repository.initialSetupEvent()
}
