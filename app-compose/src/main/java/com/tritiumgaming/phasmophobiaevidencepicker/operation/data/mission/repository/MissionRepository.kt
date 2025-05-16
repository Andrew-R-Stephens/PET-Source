package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.util.ResourceUtils
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.local.MissionLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.missions.Mission

class MissionRepository(
    context: Context,
    localSource: MissionLocalDataSource
) {

    val missions: List<Mission> = localSource.fetchMissions(context)

}