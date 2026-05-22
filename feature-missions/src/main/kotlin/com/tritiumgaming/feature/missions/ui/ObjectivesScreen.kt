package com.tritiumgaming.feature.missions.ui

import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.feature.missions.ui.components.mission.MissionSpinnerUiState
import com.tritiumgaming.feature.missions.ui.components.mission.MissionWrapperActions
import com.tritiumgaming.feature.missions.ui.components.name.GhostNameUiActions
import com.tritiumgaming.feature.missions.ui.components.name.NamesSpinnerUiState
import com.tritiumgaming.feature.missions.ui.components.response.GhostResponseUiActions
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentLandscape
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentPortrait
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentUiState
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType

@Composable
@Preview(name = "Small Phone", device = "id:small_phone")
private fun ObjectivesScreenPreview_SmallPhone_Portrait() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface
        ) {
            ObjectivesScreenContent(
                deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
                objectivesContentUiState = ObjectivesContentUiState(
                    ghostResponseUiState = DifficultyResponseType.KNOWN,
                    missionSpinnerUiState = MissionSpinnerUiState(),
                    ghostDetailsUiState = GhostDetailsUiState(),
                    namesSpinnerUiState = NamesSpinnerUiState()
                ),
                ghostNameUiActions = GhostNameUiActions({}, {}),
                ghostResponseUiActions = GhostResponseUiActions({}),
                missionWrapperActions = MissionWrapperActions({ _, _ -> }, { _, _ -> })
            )
        }
    }
}

@Composable
@Preview(name = "Small Phone", device = "spec:parent=small_phone,orientation=landscape")
private fun ObjectivesScreenPreview_SmallPhone_Landscape() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface
        ) {
            ObjectivesScreenContent(
                deviceConfiguration = DeviceConfiguration.MOBILE_LANDSCAPE,
                objectivesContentUiState = ObjectivesContentUiState(
                    ghostResponseUiState = DifficultyResponseType.KNOWN,
                    missionSpinnerUiState = MissionSpinnerUiState(),
                    ghostDetailsUiState = GhostDetailsUiState(),
                    namesSpinnerUiState = NamesSpinnerUiState()
                ),
                ghostNameUiActions = GhostNameUiActions({}, {}),
                ghostResponseUiActions = GhostResponseUiActions({}),
                missionWrapperActions = MissionWrapperActions({ _, _ -> }, { _, _ -> })
            )
        }
    }
}

@Composable
@Preview(name = "Medium Phone Portrait",
    device = "spec:width=411dp,height=891dp"
)
private fun ObjectivesScreenPreview_MediumPhone_Portrait() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface
        ) {
            ObjectivesScreenContent(
                deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
                objectivesContentUiState = ObjectivesContentUiState(
                    ghostResponseUiState = DifficultyResponseType.KNOWN,
                    missionSpinnerUiState = MissionSpinnerUiState(),
                    ghostDetailsUiState = GhostDetailsUiState(),
                    namesSpinnerUiState = NamesSpinnerUiState()
                ),
                ghostNameUiActions = GhostNameUiActions({}, {}),
                ghostResponseUiActions = GhostResponseUiActions({}),
                missionWrapperActions = MissionWrapperActions({ _, _ -> }, { _, _ -> })
            )
        }
    }
}

@Composable
@Preview(name = "Medium Phone Landscape",
    device = "spec:width=411dp,height=891dp,orientation=landscape"
)
private fun ObjectivesScreenPreview_MediumPhone_Landscape() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface
        ) {
            ObjectivesScreenContent(
                deviceConfiguration = DeviceConfiguration.MOBILE_LANDSCAPE,
                objectivesContentUiState = ObjectivesContentUiState(
                    ghostResponseUiState = DifficultyResponseType.KNOWN,
                    missionSpinnerUiState = MissionSpinnerUiState(),
                    ghostDetailsUiState = GhostDetailsUiState(),
                    namesSpinnerUiState = NamesSpinnerUiState()
                ),
                ghostNameUiActions = GhostNameUiActions({}, {}),
                ghostResponseUiActions = GhostResponseUiActions({}),
                missionWrapperActions = MissionWrapperActions({ _, _ -> }, { _, _ -> })
            )
        }
    }
}

@Composable
@Preview(name = "Medium Tablet Portrait",
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait"
)
private fun ObjectivesScreenPreview_MediumTablet_Portrait() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface
        ) {
            ObjectivesScreenContent(
                deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
                objectivesContentUiState = ObjectivesContentUiState(
                    ghostResponseUiState = DifficultyResponseType.KNOWN,
                    missionSpinnerUiState = MissionSpinnerUiState(),
                    ghostDetailsUiState = GhostDetailsUiState(),
                    namesSpinnerUiState = NamesSpinnerUiState()
                ),
                ghostNameUiActions = GhostNameUiActions({}, {}),
                ghostResponseUiActions = GhostResponseUiActions({}),
                missionWrapperActions = MissionWrapperActions({ _, _ -> }, { _, _ -> })
            )
        }
    }
}

@Composable
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
private fun ObjectivesScreenPreview_MediumTablet_Landscape() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface
        ) {
            ObjectivesScreenContent(
                deviceConfiguration = DeviceConfiguration.TABLET_LANDSCAPE,
                objectivesContentUiState = ObjectivesContentUiState(
                    ghostResponseUiState = DifficultyResponseType.KNOWN,
                    missionSpinnerUiState = MissionSpinnerUiState(),
                    ghostDetailsUiState = GhostDetailsUiState(),
                    namesSpinnerUiState = NamesSpinnerUiState()
                ),
                ghostNameUiActions = GhostNameUiActions({}, {}),
                ghostResponseUiActions = GhostResponseUiActions({}),
                missionWrapperActions = MissionWrapperActions({ _, _ -> }, { _, _ -> })
            )
        }
    }
}

@Composable
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private fun ObjectivesScreenPreview_Foldable() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface
        ) {
            ObjectivesScreenContent(
                deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
                objectivesContentUiState = ObjectivesContentUiState(
                    ghostResponseUiState = DifficultyResponseType.KNOWN,
                    missionSpinnerUiState = MissionSpinnerUiState(),
                    ghostDetailsUiState = GhostDetailsUiState(),
                    namesSpinnerUiState = NamesSpinnerUiState()
                ),
                ghostNameUiActions = GhostNameUiActions({}, {}),
                ghostResponseUiActions = GhostResponseUiActions({}),
                missionWrapperActions = MissionWrapperActions({ _, _ -> }, { _, _ -> })
            )
        }
    }
}

@Composable
fun ObjectivesScreen(
    objectivesViewModel: ObjectivesViewModel
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val namesSpinnerUiState by objectivesViewModel.namesSpinnerUiState.collectAsStateWithLifecycle()
    val ghostDetailsUiState by objectivesViewModel.ghostDetailsUiState.collectAsStateWithLifecycle()
    val missionSpinnerUiState by objectivesViewModel.missionSpinnerUiState.collectAsStateWithLifecycle()
    val difficultyState by objectivesViewModel.difficultyState.collectAsStateWithLifecycle()

    val objectivesContentUiState = ObjectivesContentUiState(
        ghostResponseUiState = difficultyState.responseType,
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

    ObjectivesScreenContent(
        deviceConfiguration,
        objectivesContentUiState,
        ghostNameUiActions,
        ghostResponseUiActions,
        missionWrapperActions
    )

}

@Composable
private fun ObjectivesScreenContent(
    deviceConfiguration: DeviceConfiguration,
    objectivesContentUiState: ObjectivesContentUiState,
    ghostNameUiActions: GhostNameUiActions,
    ghostResponseUiActions: GhostResponseUiActions,
    missionWrapperActions: MissionWrapperActions
) {
    when (deviceConfiguration) {
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
