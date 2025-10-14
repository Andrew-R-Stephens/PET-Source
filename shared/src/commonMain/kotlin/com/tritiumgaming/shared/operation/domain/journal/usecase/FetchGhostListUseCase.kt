package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.ghost.model.Ghost
import com.tritiumgaming.shared.operation.domain.ghost.repository.GhostRepository

class FetchGhostListUseCase(
    private val repository: GhostRepository
) {
    operator fun invoke(): Result<List<Ghost>> {
        val result = repository.fetchGhosts()

        return result
    }
}
    