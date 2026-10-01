package com.tritiumgaming.data.journal.usecase

import com.tritiumgaming.data.ghost.model.GhostType
import com.tritiumgaming.data.ghost.repository.GhostRepository

class FetchGhostTypesUseCase(
    private val repository: GhostRepository
)  {
    operator fun invoke(): List<GhostType> {
        val result = repository.fetchGhostTypes()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList())
    }
}