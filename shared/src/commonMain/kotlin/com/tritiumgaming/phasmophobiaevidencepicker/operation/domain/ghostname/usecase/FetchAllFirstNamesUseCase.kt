package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.model.GhostName
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostname.repository.GhostNameRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.repository.MissionRepository

class FetchAllFirstNamesUseCase(
    private val repository: GhostNameRepository
) {
    operator fun invoke(): Result<List<GhostName>> {

        val result = repository.getNamesBy(GhostName.NamePriority.FIRST)

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get first names", it)) }

        return result
    }
}
    