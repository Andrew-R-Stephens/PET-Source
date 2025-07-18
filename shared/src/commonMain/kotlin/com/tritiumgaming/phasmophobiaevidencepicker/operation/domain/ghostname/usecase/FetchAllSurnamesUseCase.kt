package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.model.GhostName
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.repository.GhostNameRepository

class FetchAllSurnamesUseCase(
    private val repository: GhostNameRepository
) {
    operator fun invoke(): Result<List<GhostName>> {

        val result = repository.getNamesBy(GhostName.NamePriority.SURNAME)

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get surnames", it)) }

        return result
    }
}
    