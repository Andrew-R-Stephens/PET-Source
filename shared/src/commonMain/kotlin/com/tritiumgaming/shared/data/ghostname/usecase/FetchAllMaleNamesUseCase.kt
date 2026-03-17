package com.tritiumgaming.shared.data.ghostname.usecase

import com.tritiumgaming.shared.data.ghostname.model.GhostName
import com.tritiumgaming.shared.data.ghostname.model.GhostName.Gender.MALE
import com.tritiumgaming.shared.data.ghostname.model.GhostName.NamePriority.FIRST
import com.tritiumgaming.shared.data.ghostname.repository.GhostNameRepository

class FetchAllMaleNamesUseCase(
    private val repository: GhostNameRepository
) {
    operator fun invoke(): Result<List<GhostName>> {

        val result = repository.getNamesBy(
            FIRST,
            MALE
        )

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get male names", it)) }

        return result
    }
}
    