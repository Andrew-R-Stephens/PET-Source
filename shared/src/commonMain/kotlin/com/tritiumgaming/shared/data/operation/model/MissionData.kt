package com.tritiumgaming.shared.data.operation.model

import com.tritiumgaming.shared.data.mission.model.Mission

typealias MissionStatus = Boolean

data class MissionState(
    val mission: Mission,
    val status: MissionStatus = false
)

data class MissionData(
    val selectedMissions: List<MissionState> = emptyList()
) {
    companion object {
        const val MISSION_NOT_COMPLETE: MissionStatus = false
        const val MISSION_COMPLETE: MissionStatus = true
    }
}
