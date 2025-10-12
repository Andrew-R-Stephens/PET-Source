package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.evidence.repository.EvidenceRepository

class FetchEvidencesUseCase(
        private val repository: EvidenceRepository
    ) {
        operator fun invoke(): List<EvidenceType> {
            val result = repository.fetchEvidence()

            result.exceptionOrNull()?.printStackTrace()

            return result.getOrDefault(emptyList())
        }
    }
    