package com.tritiumgaming.shared.data.journal.usecase

class GetEvidenceTypeByIdUseCase(
    private val repository: com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository
) {
    operator fun invoke(evidenceId: com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier): com.tritiumgaming.shared.data.evidence.model.EvidenceType? {
        val result = repository.fetchEvidenceTypes()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList()).find { it.id == evidenceId }
    }
}