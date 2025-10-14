package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.evidence.model.Evidence
import com.tritiumgaming.shared.operation.domain.evidence.repository.EvidenceRepository

class FetchEvidenceListUseCase(
    private val repository: EvidenceRepository
) {
    operator fun invoke(): Result<List<Evidence>> {
        val result = repository.fetchEvidence()

        return result
    }
}
    