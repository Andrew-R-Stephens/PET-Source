package com.tritiumgaming.data.journal.usecase

import com.tritiumgaming.data.evidence.model.EvidenceType
import com.tritiumgaming.data.evidence.repository.EvidenceRepository

class FetchEvidenceTypesUseCase(
    private val repository: EvidenceRepository
) {
    operator fun invoke(): Result<List<EvidenceType>> {
        val result = repository.fetchEvidenceTypes()

        return result
    }
}
    