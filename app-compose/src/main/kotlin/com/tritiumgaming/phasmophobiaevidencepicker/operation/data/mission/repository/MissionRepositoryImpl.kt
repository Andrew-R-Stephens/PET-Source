package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.MissionDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.repository.MissionRepository

class MissionRepositoryImpl(
    private val localSource: MissionDataSource
): MissionRepository {

    var missions: List<Mission> = emptyList()

    override fun getMissions(): Result<List<Mission>> {

        if(missions.isEmpty()) {
            val result = localSource.get().map { dto -> dto.toDomain() }

            result.exceptionOrNull()?.let {
                return Result.failure(Exception("Could not get missions", it))
            }

            result.getOrNull()?.let { missions = it }
        }

        return localSource.get().map { dto -> dto.toDomain() }
    }

}
