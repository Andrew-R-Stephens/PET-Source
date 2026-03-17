package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.investigation.InvestigationRepository
import com.tritiumgaming.shared.data.investigation.model.EvidenceState


class UpdateInvestigationEvidenceUseCase(private val repository: InvestigationRepository) {
    operator fun invoke(evidence: List<EvidenceState>) = repository.updateEvidence(evidence)
}
