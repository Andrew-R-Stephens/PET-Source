package com.tritiumgaming.shared.data.ghostname.usecase

import com.tritiumgaming.shared.data.ghostname.model.GhostName.Gender.*
import com.tritiumgaming.shared.data.ghostname.model.GhostName.NamePriority.*

class FetchAllFemaleNamesUseCase(
    private val repository: com.tritiumgaming.shared.data.ghostname.repository.GhostNameRepository
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.ghostname.model.GhostName>> {

        val result = repository.getNamesBy(
            FIRST,
            FEMALE
        )

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get female names", it)) }

        return result
    }
}
    