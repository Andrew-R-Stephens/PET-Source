package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.investigation.InvestigationRepository
import com.tritiumgaming.shared.data.investigation.model.PhaseData

class UpdateInvestigationPhaseUseCase(private val repository: InvestigationRepository) {
    operator fun invoke(phase: PhaseData) = repository.updatePhase(phase)
}
