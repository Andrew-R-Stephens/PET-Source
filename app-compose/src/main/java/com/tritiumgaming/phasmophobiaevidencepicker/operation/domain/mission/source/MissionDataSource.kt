package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.source

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission

interface MissionDataSource {

    fun fetchMissions(context: Context): List<Mission>

}