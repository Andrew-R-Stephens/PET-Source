package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.evidence.model.Evidence
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository

class GetEvidenceUseCase(
    private val repository: EvidenceRepository
) {
    operator fun invoke(evidenceType: EvidenceType): Result<Evidence> {
        val result = repository.fetchEvidences()

        val evidence = result
            .getOrThrow()
            .find { it.id == evidenceType.id }
            ?: return Result.failure(Exception("Evidence not found"))

        return Result.success(evidence)
    }
}
    