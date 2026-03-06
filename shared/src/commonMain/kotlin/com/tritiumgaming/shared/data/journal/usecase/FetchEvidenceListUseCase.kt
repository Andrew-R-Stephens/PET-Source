package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.evidence.model.Evidence
import com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository

class FetchEvidenceListUseCase(
    private val repository: EvidenceRepository
) {
    operator fun invoke(): Result<List<Evidence>> {
        val result = repository.fetchEvidences()

        return result
    }
}
    