package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.ghost.model.GhostType
import com.tritiumgaming.shared.data.ghost.repository.GhostRepository

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
}
    