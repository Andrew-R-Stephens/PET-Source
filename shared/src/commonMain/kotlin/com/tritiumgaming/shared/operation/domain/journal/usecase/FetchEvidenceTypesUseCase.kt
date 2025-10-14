package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.evidence.repository.EvidenceRepository

class FetchEvidenceTypesUseCase(
    private val repository: EvidenceRepository
) {
    operator fun invoke(): Result<List<EvidenceType>> {
        val result = repository.fetchEvidenceType()

        return result
    }
}
    