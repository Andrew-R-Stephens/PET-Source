package com.tritiumgaming.shared.data.ghostname.usecase

import com.tritiumgaming.shared.data.ghostname.model.GhostName
import com.tritiumgaming.shared.data.ghostname.model.GhostName.NamePriority.FIRST
import com.tritiumgaming.shared.data.ghostname.repository.GhostNameRepository

class FetchAllFirstNamesUseCase(
    private val repository: GhostNameRepository
) {
    operator fun invoke(): Result<List<GhostName>> {

        val result = repository.getNamesBy(FIRST)

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get first names", it)) }

        return result
    }
}
    