package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.investigation.InvestigationRepository

class ResetInvestigationUseCase(private val repository: InvestigationRepository) {
    operator fun invoke() = repository.reset()
}
