package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.evidence.repository.EvidenceRepository

class GetEvidenceByIdUseCase(
    private val repository: EvidenceRepository
) {
    operator fun invoke(evidenceId: String): EvidenceType? {
        val result = repository.fetchEvidence()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList()).find { it.id == evidenceId }
    }
}