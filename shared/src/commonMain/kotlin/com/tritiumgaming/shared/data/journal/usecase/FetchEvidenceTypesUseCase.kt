package com.tritiumgaming.shared.data.journal.usecase

class FetchEvidenceTypesUseCase(
    private val repository: com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.evidence.model.EvidenceType>> {
        val result = repository.fetchEvidenceType()

        return result
    }
}
    