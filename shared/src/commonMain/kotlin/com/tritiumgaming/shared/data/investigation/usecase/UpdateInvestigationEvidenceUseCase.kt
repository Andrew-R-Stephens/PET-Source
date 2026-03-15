package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.evidence.model.EvidenceState
import com.tritiumgaming.shared.data.investigation.InvestigationRepository


class UpdateInvestigationEvidenceUseCase(private val repository: InvestigationRepository) {
    operator fun invoke(evidence: List<EvidenceState>) = repository.updateEvidence(evidence)
}
