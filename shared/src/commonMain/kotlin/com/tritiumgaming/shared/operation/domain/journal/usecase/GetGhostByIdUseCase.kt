package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.journal.model.GhostType
import com.tritiumgaming.shared.operation.domain.journal.repository.JournalRepository

class GetGhostByIdUseCase(
        private val journalRepository: JournalRepository
    ) {
        operator fun invoke(ghostId: String): GhostType? {
            val result = journalRepository.fetchGhosts()
            
            result.exceptionOrNull()?.printStackTrace()
            
            return result.getOrDefault(emptyList()).find { it.id == ghostId }
        }
    }