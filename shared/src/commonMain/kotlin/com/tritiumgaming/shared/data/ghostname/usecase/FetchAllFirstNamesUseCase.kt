package com.tritiumgaming.shared.data.ghostname.usecase

class FetchAllFirstNamesUseCase(
    private val repository: com.tritiumgaming.shared.data.ghostname.repository.GhostNameRepository
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.ghostname.model.GhostName>> {

        val result = repository.getNamesBy(com.tritiumgaming.shared.data.ghostname.model.GhostName.NamePriority.FIRST)

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get first names", it)) }

        return result
    }
}
    