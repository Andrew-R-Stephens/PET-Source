package com.tritiumgaming.data.ghostname.usecase

import com.tritiumgaming.data.ghostname.model.GhostName
import com.tritiumgaming.data.ghostname.model.GhostName.NamePriority
import com.tritiumgaming.data.ghostname.repository.GhostNameRepository

class FetchAllFirstNamesUseCase(
    private val repository: GhostNameRepository
) {
    operator fun invoke(): Result<List<GhostName>> {

        val result = repository.getNamesBy(NamePriority.FIRST)

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get first names", it)) }

        return result
    }
}
    