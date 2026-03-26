package com.tritiumgaming.feature.missions.ui.screens

import com.tritiumgaming.feature.missions.ui.GhostDetailsUiState
import com.tritiumgaming.feature.missions.ui.components.mission.MissionSpinnerUiState
import com.tritiumgaming.feature.missions.ui.components.name.NamesSpinnerUiState
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType

data class ObjectivesContentUiState(
    val ghostResponseUiState: DifficultyResponseType,
    val missionSpinnerUiState: MissionSpinnerUiState,
    val ghostDetailsUiState: GhostDetailsUiState,
    val namesSpinnerUiState: NamesSpinnerUiState,
)