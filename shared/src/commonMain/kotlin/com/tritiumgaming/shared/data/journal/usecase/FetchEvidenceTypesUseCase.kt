package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository

class FetchEvidenceTypesUseCase(
    private val repository: EvidenceRepository
) {
    operator fun invoke(): Result<List<EvidenceType>> {
        val result = repository.fetchEvidenceTypes()

        return result
    }
}
    