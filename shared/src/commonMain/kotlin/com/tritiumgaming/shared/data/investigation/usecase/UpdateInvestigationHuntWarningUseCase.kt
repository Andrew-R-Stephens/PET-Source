package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.investigation.InvestigationRepository

class UpdateInvestigationHuntWarningUseCase(private val repository: InvestigationRepository) {
    operator fun invoke(warning: Boolean) = repository.updateHuntWarning(warning)
}
