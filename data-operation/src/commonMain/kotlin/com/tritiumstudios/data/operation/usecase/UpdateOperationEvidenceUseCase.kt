package com.tritiumstudios.data.operation.usecase

import com.tritiumgaming.data.journal.model.EvidenceState
import com.tritiumstudios.data.operation.OperationRepository


class UpdateOperationEvidenceUseCase(private val repository: OperationRepository) {
    operator fun invoke(evidence: List<EvidenceState>) = repository.updateEvidence(evidence)
}
