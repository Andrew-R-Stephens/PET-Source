package com.tritiumgaming.feature.missions.ui

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.feature.missions.ui.components.MissionWrapperActions
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentLandscape
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentPortrait

@Composable
fun ObjectivesScreen(
    objectivesViewModel: ObjectivesViewModel,
    difficultyUiState: DifficultyUiState
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val namesSpinnerUiState by objectivesViewModel.namesSpinnerUiState.collectAsStateWithLifecycle()
    //val missionUiState by objectivesViewModel.missionUiState.collectAsStateWithLifecycle()
    val ghostDetailsUiState by objectivesViewModel.ghostDetailsUiState.collectAsStateWithLifecycle()
    val missionSpinnerUiState by objectivesViewModel.missionSpinnerUiState.collectAsStateWithLifecycle()

    val missionWrapperActions = MissionWrapperActions(
        onSelectMission = { index, mission ->
            objectivesViewModel.selectMission(index, mission)
        },
        onChangeMissionStatus = { mission, state ->
            objectivesViewModel.updateMissionStatus(mission, state)
        },
    )

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            ObjectivesContentPortrait(
                difficultyUiState = difficultyUiState,
                //missionsUiState = missionUiState,
                missionSpinnerUiState = missionSpinnerUiState,
                ghostDetailsUiState = ghostDetailsUiState,
                namesSpinnerUiState = namesSpinnerUiState,
                onSelectFirstName = { firstname ->
                    objectivesViewModel.setGhostFirstName(firstname)
                },
                onSelectSurname = { surname ->
                    objectivesViewModel.setGhostSurname(surname)
                },
                onResponseChange = { response ->
                    objectivesViewModel.setGhostResponse(response)
                },
                missionWrapperActions = missionWrapperActions,
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            ObjectivesContentLandscape(
                difficultyUiState = difficultyUiState,
                //missionsUiState = missionUiState,
                missionSpinnerUiState = missionSpinnerUiState,
                ghostDetailsUiState = ghostDetailsUiState,
                namesSpinnerUiState = namesSpinnerUiState,
                onSelectFirstName = { firstname ->
                    objectivesViewModel.setGhostFirstName(firstname)
                },
                onSelectSurname = { surname ->
                    objectivesViewModel.setGhostSurname(surname)
                },
                onResponseChange = { response ->
                    objectivesViewModel.setGhostResponse(response)
                },
                missionWrapperActions = missionWrapperActions,
            )
        }
    }

}
