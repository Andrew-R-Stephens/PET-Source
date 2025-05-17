package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.source.MissionDataSource

interface MissionRepository {
    val localSource: MissionDataSource

    val missions: List<Mission>
}