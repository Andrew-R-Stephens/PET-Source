package com.tritiumgaming.data.journal.usecase

import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.data.ghost.model.GhostType
import com.tritiumgaming.data.ghost.repository.GhostRepository

class GetGhostTypeByIdUseCase(
    private val repository: GhostRepository
) {
    operator fun invoke(ghostId: GhostIdentifier): Result<GhostType> {
        val result = repository.fetchGhostTypes().getOrThrow()

        val ghostType = result.firstOrNull { it.id == ghostId }
            ?: return Result.failure(Exception("Ghost not found"))

        return Result.success(ghostType)
    }
}