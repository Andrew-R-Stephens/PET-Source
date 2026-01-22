package com.tritiumgaming.feature.missions.ui

import com.tritiumgaming.shared.data.mission.model.Mission

data class MissionSpinnerUiState(
    val selectedMissions: List<MissionUiState> = emptyList(),
    val availableMissions: List<Mission> = emptyList(),
)
