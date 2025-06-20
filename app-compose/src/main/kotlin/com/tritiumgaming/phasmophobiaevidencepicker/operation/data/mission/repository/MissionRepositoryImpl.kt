package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.repository.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.MissionDataSource

class MissionRepositoryImpl(
    private val localSource: MissionDataSource
): MissionRepository {

    override fun getMissions(): Result<List<Mission>> =
        localSource.get().map { dto -> dto.toDomain() }

}