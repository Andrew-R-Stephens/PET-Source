package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.journal.repository.JournalRepository

class GetEvidenceByIdUseCase(
    private val journalRepository: JournalRepository
) {
    operator fun invoke(evidenceId: String): EvidenceType? {
        val result = journalRepository.fetchEvidence()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList()).find { it.id == evidenceId }
    }
}