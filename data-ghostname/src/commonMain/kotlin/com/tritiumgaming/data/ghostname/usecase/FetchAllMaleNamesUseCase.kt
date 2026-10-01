package com.tritiumgaming.data.ghostname.usecase

import com.tritiumgaming.data.ghostname.model.GhostName
import com.tritiumgaming.data.ghostname.model.GhostName.Gender
import com.tritiumgaming.data.ghostname.model.GhostName.NamePriority
import com.tritiumgaming.data.ghostname.repository.GhostNameRepository

class FetchAllMaleNamesUseCase(
    private val repository: GhostNameRepository
) {
    operator fun invoke(): Result<List<GhostName>> {

        val result = repository.getNamesBy(
            NamePriority.FIRST,
            Gender.MALE
        )

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get male names", it)) }

        return result
    }
}
    