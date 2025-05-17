package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.repository.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.source.MissionDataSource

class MissionRepositoryImpl(
    context: Context,
    override val localSource: MissionDataSource
): MissionRepository {

    override val missions: List<Mission> = localSource.fetchMissions(context)

}