package com.tritiumgaming.feature.missions.ui.screens

import com.tritiumgaming.feature.missions.ui.GhostDetailsUiState
import com.tritiumgaming.feature.missions.ui.components.mission.MissionSpinnerUiState
import com.tritiumgaming.feature.missions.ui.components.name.NamesSpinnerUiState
import com.tritiumgaming.feature.missions.ui.components.response.GhostResponseUiState

data class ObjectivesContentUiState(
    val ghostResponseUiState: GhostResponseUiState,
    val missionSpinnerUiState: MissionSpinnerUiState,
    val ghostDetailsUiState: GhostDetailsUiState,
    val namesSpinnerUiState: NamesSpinnerUiState,
)