package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.evidence.model.Evidence
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.evidence.repository.EvidenceRepository

class GetEvidenceUseCase(
    private val repository: EvidenceRepository
) {
    operator fun invoke(evidenceType: EvidenceType): Result<Evidence> {
        val result = repository.fetchEvidence()

        val evidence = result
            .getOrThrow()
            .find { it.id == evidenceType.id }
            ?: return Result.failure(Exception("Evidence not found"))

        return Result.success(evidence)
    }
}
    