package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.ghost.model.GhostType
import com.tritiumgaming.shared.operation.domain.ghost.repository.GhostRepository

class GetGhostTypeByIdUseCase(
        private val repository: GhostRepository
    ) {
        operator fun invoke(ghostId: String): GhostType? {
            val result = repository.fetchGhostTypes()
            
            result.exceptionOrNull()?.printStackTrace()
            
            return result.getOrDefault(emptyList()).find { it.id == ghostId }
        }
    }