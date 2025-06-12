package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.repository.JournalRepository

class FetchEvidencesUseCase(
        private val journalRepository: JournalRepository
    ) {
        operator fun invoke(): List<EvidenceType> {
            val result = journalRepository.fetchEvidence()

            result.exceptionOrNull()?.printStackTrace()

            return result.getOrDefault(emptyList())
        }
    }
    