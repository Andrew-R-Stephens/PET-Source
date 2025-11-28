package com.tritiumgaming.shared.data.journal.usecase

class FetchGhostTypesUseCase(
    private val repository: com.tritiumgaming.shared.data.ghost.repository.GhostRepository
)  {
    operator fun invoke(): List<com.tritiumgaming.shared.data.ghost.model.GhostType> {
        val result = repository.fetchGhostTypes()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList())
    }
}