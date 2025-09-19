package com.tritiumgaming.shared.operation.domain.ghostname.usecase

import com.tritiumgaming.shared.operation.domain.ghostname.model.GhostName
import com.tritiumgaming.shared.operation.domain.ghostname.repository.GhostNameRepository

class FetchAllFemaleNamesUseCase(
    private val repository: GhostNameRepository
) {
    operator fun invoke(): Result<List<GhostName>> {

        val result = repository.getNamesBy(
            GhostName.NamePriority.FIRST,
            GhostName.Gender.FEMALE
        )

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get female names", it)) }

        return result
    }
}
    