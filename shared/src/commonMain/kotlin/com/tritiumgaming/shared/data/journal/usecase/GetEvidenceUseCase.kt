package com.tritiumgaming.shared.data.journal.usecase

class GetEvidenceUseCase(
    private val repository: com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository
) {
    operator fun invoke(evidenceType: com.tritiumgaming.shared.data.evidence.model.EvidenceType): Result<com.tritiumgaming.shared.data.evidence.model.Evidence> {
        val result = repository.fetchEvidence()

        val evidence = result
            .getOrThrow()
            .find { it.id == evidenceType.id }
            ?: return Result.failure(Exception("Evidence not found"))

        return Result.success(evidence)
    }
}
    