package com.tritiumgaming.shared.operation.domain.ghostname.usecase

import com.tritiumgaming.shared.operation.domain.ghostname.model.GhostName
import com.tritiumgaming.shared.operation.domain.ghostname.repository.GhostNameRepository

class FetchAllMaleNamesUseCase(
    private val repository: GhostNameRepository
) {
    operator fun invoke(): Result<List<GhostName>> {

        val result = repository.getNamesBy(
            GhostName.NamePriority.FIRST,
            GhostName.Gender.MALE
        )

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get male names", it)) }

        return result
    }
}
    