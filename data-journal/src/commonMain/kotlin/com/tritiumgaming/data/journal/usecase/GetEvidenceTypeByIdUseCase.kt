package com.tritiumgaming.data.journal.usecase

import com.tritiumgaming.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.data.evidence.model.EvidenceType
import com.tritiumgaming.data.evidence.repository.EvidenceRepository

class GetEvidenceTypeByIdUseCase(
    private val repository: EvidenceRepository
) {
    operator fun invoke(evidenceId: EvidenceIdentifier): EvidenceType? {
        val result = repository.fetchEvidenceTypes()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList()).find { it.id == evidenceId }
    }
}