package com.tritiumgaming.shared.data.journal.usecase

class FetchGhostListUseCase(
    private val repository: com.tritiumgaming.shared.data.ghost.repository.GhostRepository
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.ghost.model.Ghost>> {
        val result = repository.fetchGhosts()

        return result
    }
}
    