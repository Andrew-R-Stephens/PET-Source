package com.tritiumgaming.feature.missions.ui.components.mission

import com.tritiumgaming.data.mission.model.Mission

data class MissionSpinnerUiState(
    val selectedMissions: List<MissionUiState> = emptyList(),
    val availableMissions: List<Mission> = emptyList(),
)
