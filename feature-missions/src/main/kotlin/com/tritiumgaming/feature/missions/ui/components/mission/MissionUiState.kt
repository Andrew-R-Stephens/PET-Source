package com.tritiumgaming.feature.missions.ui.components.mission

import com.tritiumgaming.shared.data.mission.model.Mission
import com.tritiumgaming.shared.data.operation.model.MissionStatus

data class MissionUiState(
    val mission: Mission,
    val status: MissionStatus
)
