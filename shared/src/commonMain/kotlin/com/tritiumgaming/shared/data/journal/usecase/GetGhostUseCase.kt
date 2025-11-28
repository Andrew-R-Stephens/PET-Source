package com.tritiumgaming.shared.data.journal.usecase

class GetGhostUseCase(
    private val repository: com.tritiumgaming.shared.data.ghost.repository.GhostRepository
) {
    operator fun invoke(ghostType: com.tritiumgaming.shared.data.ghost.model.GhostType): Result<com.tritiumgaming.shared.data.ghost.model.Ghost> {
        val result = repository.fetchGhosts()

        val ghost = result
            .getOrThrow()
            .find { it.id == ghostType.id }
            ?: return Result.failure(Exception("Ghost not found"))

        return Result.success(ghost)
    }
}
    