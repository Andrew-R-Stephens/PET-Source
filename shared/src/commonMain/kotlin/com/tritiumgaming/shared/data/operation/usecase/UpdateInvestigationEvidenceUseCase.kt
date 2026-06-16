package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.operation.model.EvidenceState


class UpdateInvestigationEvidenceUseCase(private val repository: OperationRepository) {
    operator fun invoke(evidence: List<EvidenceState>) = repository.updateEvidence(evidence)
}
