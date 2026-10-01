package com.tritiumgaming.data.journal.usecase

import com.tritiumgaming.data.ghost.model.Ghost
import com.tritiumgaming.data.ghost.repository.GhostRepository

class FetchGhostListUseCase(
    private val repository: GhostRepository
) {
    operator fun invoke(): Result<List<Ghost>> {
        val result = repository.fetchGhosts()

        return result
    }
}
    