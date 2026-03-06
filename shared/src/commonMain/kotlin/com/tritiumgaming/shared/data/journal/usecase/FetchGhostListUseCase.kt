package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.ghost.repository.GhostRepository

class FetchGhostListUseCase(
    private val repository: GhostRepository
) {
    operator fun invoke(): Result<List<Ghost>> {
        val result = repository.fetchGhosts()

        return result
    }
}
    