package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.ghost.model.GhostType
import com.tritiumgaming.shared.data.ghost.repository.GhostRepository

class FetchGhostTypesUseCase(
    private val repository: GhostRepository
)  {
    operator fun invoke(): List<GhostType> {
        val result = repository.fetchGhostTypes()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList())
    }
}