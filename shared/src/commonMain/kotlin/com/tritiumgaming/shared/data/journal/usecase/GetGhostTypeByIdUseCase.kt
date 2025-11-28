package com.tritiumgaming.shared.data.journal.usecase

class GetGhostTypeByIdUseCase(
        private val repository: com.tritiumgaming.shared.data.ghost.repository.GhostRepository
    ) {
        operator fun invoke(ghostId: com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier): com.tritiumgaming.shared.data.ghost.model.GhostType? {
            val result = repository.fetchGhostTypes()
            
            result.exceptionOrNull()?.printStackTrace()
            
            return result.getOrDefault(emptyList()).find { it.id == ghostId }
        }
    }