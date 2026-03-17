package com.tritiumgaming.shared.data.ghostname.usecase

import com.tritiumgaming.shared.data.ghostname.model.GhostName
import com.tritiumgaming.shared.data.ghostname.model.GhostName.NamePriority.SURNAME
import com.tritiumgaming.shared.data.ghostname.repository.GhostNameRepository

class FetchAllSurnamesUseCase(
    private val repository: GhostNameRepository
) {
    operator fun invoke(): Result<List<GhostName>> {

        val result = repository.getNamesBy(SURNAME)

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get surnames", it)) }

        return result
    }
}
    