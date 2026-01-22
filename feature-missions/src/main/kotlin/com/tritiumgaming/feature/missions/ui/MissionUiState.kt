package com.tritiumgaming.feature.missions.ui

import com.tritiumgaming.shared.data.mission.model.Mission

typealias MissionStatus = Boolean

data class MissionUiState(
    val mission: Mission,
    val status: MissionStatus
)
