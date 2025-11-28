package com.tritiumgaming.shared.data.ghostname.usecase

class FetchAllFemaleNamesUseCase(
    private val repository: com.tritiumgaming.shared.data.ghostname.repository.GhostNameRepository
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.ghostname.model.GhostName>> {

        val result = repository.getNamesBy(
            com.tritiumgaming.shared.data.ghostname.model.GhostName.NamePriority.FIRST,
            com.tritiumgaming.shared.data.ghostname.model.GhostName.Gender.FEMALE
        )

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get female names", it)) }

        return result
    }
}
    