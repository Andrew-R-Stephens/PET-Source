package com.tritiumgaming.shared.data.journal.usecase

class FetchEvidenceListUseCase(
    private val repository: com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.evidence.model.Evidence>> {
        val result = repository.fetchEvidence()

        return result
    }
}
    