package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.GhostType
import com.tritiumgaming.shared.data.ghost.repository.GhostRepository

class GetGhostTypeByIdUseCase(
    private val repository: GhostRepository
) {
    operator fun invoke(ghostId: GhostResources.GhostIdentifier): Result<GhostType> {
        val result = repository.fetchGhostTypes().getOrThrow()

        val ghostType = result.firstOrNull { it.id == ghostId }
            ?: return Result.failure(Exception("Ghost not found"))

        return Result.success(ghostType)
    }
}