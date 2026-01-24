package com.tritiumgaming.feature.missions.ui

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.feature.missions.ui.components.response.GhostResponseUiState
import com.tritiumgaming.feature.missions.ui.components.name.GhostNameUiActions
import com.tritiumgaming.feature.missions.ui.components.response.GhostResponseUiActions
import com.tritiumgaming.feature.missions.ui.components.mission.MissionWrapperActions
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentLandscape
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentPortrait
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentUiState

@Composable
fun ObjectivesScreen(
    objectivesViewModel: ObjectivesViewModel,
    ghostResponseUiState: GhostResponseUiState
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val namesSpinnerUiState by objectivesViewModel.namesSpinnerUiState.collectAsStateWithLifecycle()
    val ghostDetailsUiState by objectivesViewModel.ghostDetailsUiState.collectAsStateWithLifecycle()
    val missionSpinnerUiState by objectivesViewModel.missionSpinnerUiState.collectAsStateWithLifecycle()

    val objectivesContentUiState = ObjectivesContentUiState(
        ghostResponseUiState = ghostResponseUiState,
        missionSpinnerUiState = missionSpinnerUiState,
        ghostDetailsUiState = ghostDetailsUiState,
        namesSpinnerUiState = namesSpinnerUiState
    )

    val ghostNameUiActions = GhostNameUiActions(
        onSelectFirstName = { firstname ->
            objectivesViewModel.setGhostFirstName(firstname)
        },
        onSelectSurname = { surname ->
            objectivesViewModel.setGhostSurname(surname)
        },
    )

    val ghostResponseUiActions = GhostResponseUiActions(
        onSelectResponse = { response ->
            objectivesViewModel.setGhostResponse(response)
        }
    )

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
                objectivesContentUiState = objectivesContentUiState,
                ghostNameUiActions = ghostNameUiActions,
                ghostResponseUiActions = ghostResponseUiActions,
                missionWrapperActions = missionWrapperActions,
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            ObjectivesContentLandscape(
                objectivesContentUiState = objectivesContentUiState,
                ghostNameUiActions = ghostNameUiActions,
                ghostResponseUiActions = ghostResponseUiActions,
                missionWrapperActions = missionWrapperActions,
            )
        }
    }

}
