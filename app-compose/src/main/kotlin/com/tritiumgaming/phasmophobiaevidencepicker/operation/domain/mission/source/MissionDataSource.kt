package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission

interface MissionDataSource {

    fun fetchMissions(): Result<List<Mission>>

}