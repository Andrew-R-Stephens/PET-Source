package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.repository.JournalRepository

class FetchGhostEvidencesUseCase(
    private val journalRepository: JournalRepository
) {
    operator fun invoke(): List<GhostEvidence> {
        val result = journalRepository.fetchGhostEvidence()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList())
    }
}
