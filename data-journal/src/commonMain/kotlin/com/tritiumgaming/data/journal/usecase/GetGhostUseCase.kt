package com.tritiumgaming.data.journal.usecase

import com.tritiumgaming.data.ghost.mapper.GhostResources
import com.tritiumgaming.data.ghost.model.Ghost
import com.tritiumgaming.data.ghost.model.GhostType
import com.tritiumgaming.data.ghost.repository.GhostRepository

class GetGhostUseCase(
    private val repository: GhostRepository
) {
    operator fun invoke(ghostType: GhostType): Result<Ghost> {
        val result = repository.fetchGhosts()

        val ghost = result.getOrThrow()
            .find { it.id == ghostType.id }
            ?: return Result.failure(Exception("Ghost not found"))

        return Result.success(ghost)
    }

    operator fun invoke(identifier: GhostResources.GhostIdentifier): Result<Ghost> {
        val result = repository.fetchGhosts()

        val ghost = result.getOrThrow()
            .find { it.id == identifier }
            ?: return Result.failure(Exception("Ghost not found"))

        return Result.success(ghost)
    }
}
    