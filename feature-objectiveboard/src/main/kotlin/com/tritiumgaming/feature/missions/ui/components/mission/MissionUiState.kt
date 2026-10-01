package com.tritiumgaming.feature.missions.ui.components.mission

import com.tritiumgaming.data.mission.model.Mission

typealias MissionStatus = Boolean

data class MissionUiState(
    val mission: Mission,
    val status: MissionStatus
)
