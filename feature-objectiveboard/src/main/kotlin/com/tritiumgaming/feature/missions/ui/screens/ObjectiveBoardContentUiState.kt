package com.tritiumgaming.feature.missions.ui.screens

import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.feature.missions.ui.GhostDetailsUiState
import com.tritiumgaming.feature.missions.ui.components.mission.MissionSpinnerUiState
import com.tritiumgaming.feature.missions.ui.components.name.NamesSpinnerUiState

data class ObjectiveBoardContentUiState(
    val ghostResponseUiState: DifficultyResponseType,
    val missionSpinnerUiState: MissionSpinnerUiState,
    val ghostDetailsUiState: GhostDetailsUiState,
    val namesSpinnerUiState: NamesSpinnerUiState,
)