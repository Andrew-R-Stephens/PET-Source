package com.tritiumgaming.feature.missions.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.feature.missions.ui.components.mission.MissionSpinnerUiState
import com.tritiumgaming.feature.missions.ui.components.mission.MissionUiState
import com.tritiumgaming.feature.missions.ui.components.mission.MissionWrapperActions
import com.tritiumgaming.feature.missions.ui.components.name.GhostNameUiActions
import com.tritiumgaming.feature.missions.ui.components.name.NamesSpinnerUiState
import com.tritiumgaming.feature.missions.ui.components.response.GhostResponseUiActions
import com.tritiumgaming.feature.missions.ui.screens.ObjectiveBoardContentUiState
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentLandscape
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentPortrait
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.mission.mappers.MissionResources
import com.tritiumgaming.shared.data.mission.model.Mission

@DevicePreviews
@Composable
private fun ObjectivesScreenPreview() {
    LocalThemeProvider {
        Surface(
            color = LocalPalette.current.surface
        ) {
            ObjectivesScreenContent(
                objectiveBoardContentUiState = ObjectiveBoardContentUiState(
                    ghostResponseUiState = DifficultyResponseType.KNOWN,
                    missionSpinnerUiState = MissionSpinnerUiState(
                        selectedMissions = listOf(
                            MissionUiState(
                                Mission(
                                    MissionResources.MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE,
                                    "4"
                                ),
                                false
                            ),
                            MissionUiState(
                                Mission(
                                    MissionResources.MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE,
                                    "5"
                                ),
                                false
                            ),
                            MissionUiState(
                                Mission(
                                    MissionResources.MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE,
                                    "6"
                                ),
                                false
                            ),
                        ),
                        availableMissions = listOf(
                            Mission(
                                MissionResources.MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE,
                                "0"
                            ),
                            Mission(
                                MissionResources.MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE,
                                "1"
                            ),
                            Mission(
                                MissionResources.MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE,
                                "2"
                            ),
                            Mission(
                                MissionResources.MissionContent.REPEL_HUNTING_GHOST_WITH_SMUDGE,
                                "3"
                            )
                        )
                    ),
                    ghostDetailsUiState = GhostDetailsUiState(),
                    namesSpinnerUiState = NamesSpinnerUiState()
                ),
                ghostNameUiActions = GhostNameUiActions({}, {}),
                ghostResponseUiActions = GhostResponseUiActions {},
                missionWrapperActions = MissionWrapperActions({ _, _ -> }, { _, _ -> })
            )
        }
    }
}

@Composable
fun ObjectivesScreen(
    objectiveBoardViewModel: ObjectiveBoardViewModel
) {

    val namesSpinnerUiState by objectiveBoardViewModel.namesSpinnerUiState.collectAsStateWithLifecycle()
    val ghostDetailsUiState by objectiveBoardViewModel.ghostDetailsUiState.collectAsStateWithLifecycle()
    val missionSpinnerUiState by objectiveBoardViewModel.missionSpinnerUiState.collectAsStateWithLifecycle()
    val difficultyState by objectiveBoardViewModel.difficultyState.collectAsStateWithLifecycle()

    val objectiveBoardContentUiState = ObjectiveBoardContentUiState(
        ghostResponseUiState = difficultyState.responseType,
        missionSpinnerUiState = missionSpinnerUiState,
        ghostDetailsUiState = ghostDetailsUiState,
        namesSpinnerUiState = namesSpinnerUiState
    )

    val ghostNameUiActions = GhostNameUiActions(
        onSelectFirstName = { firstname ->
            objectiveBoardViewModel.setGhostFirstName(firstname)
        },
        onSelectSurname = { surname ->
            objectiveBoardViewModel.setGhostSurname(surname)
        },
    )

    val ghostResponseUiActions = GhostResponseUiActions(
        onSelectResponse = { response ->
            objectiveBoardViewModel.setGhostResponse(response)
        }
    )

    val missionWrapperActions = MissionWrapperActions(
        onSelectMission = { index, mission ->
            objectiveBoardViewModel.selectMission(index, mission)
        },
        onChangeMissionStatus = { mission, state ->
            objectiveBoardViewModel.updateMissionStatus(mission, state)
        },
    )

    ObjectivesScreenContent(
        objectiveBoardContentUiState,
        ghostNameUiActions,
        ghostResponseUiActions,
        missionWrapperActions
    )

}

@Composable
private fun ObjectivesScreenContent(
    objectiveBoardContentUiState: ObjectiveBoardContentUiState,
    ghostNameUiActions: GhostNameUiActions,
    ghostResponseUiActions: GhostResponseUiActions,
    missionWrapperActions: MissionWrapperActions
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            ObjectivesContentPortrait(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(8.dp),
                objectiveBoardContentUiState = objectiveBoardContentUiState,
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                objectiveBoardContentUiState = objectiveBoardContentUiState,
                ghostNameUiActions = ghostNameUiActions,
                ghostResponseUiActions = ghostResponseUiActions,
                missionWrapperActions = missionWrapperActions,
            )
        }
    }
}
