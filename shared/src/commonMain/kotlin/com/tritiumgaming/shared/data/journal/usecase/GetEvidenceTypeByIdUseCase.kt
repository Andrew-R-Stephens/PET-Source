package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository

class GetEvidenceTypeByIdUseCase(
    private val repository: EvidenceRepository
) {
    operator fun invoke(evidenceId: EvidenceIdentifier): EvidenceType? {
        val result = repository.fetchEvidenceTypes()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList()).find { it.id == evidenceId }
    }
}