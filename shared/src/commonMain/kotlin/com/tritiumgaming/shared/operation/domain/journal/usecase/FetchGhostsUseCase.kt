package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.journal.model.GhostType
import com.tritiumgaming.shared.operation.domain.journal.repository.JournalRepository

class FetchGhostsUseCase(
    private val journalRepository: JournalRepository
)  {
    operator fun invoke(): List<GhostType> {
        val result = journalRepository.fetchGhosts()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList())
    }
}