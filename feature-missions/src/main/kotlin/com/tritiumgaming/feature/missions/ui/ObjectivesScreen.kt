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

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Small Phone", device = "id:small_phone")
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
@Preview(name = "Medium Phone Portrait", device = "spec:width=411dp,height=891dp")
@Preview(name = "Medium Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Medium Tablet Portrait", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private annotation class DevicePreviews

@DevicePreviews
@Composable
@Preview
private fun ObjectivesScreenPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface
        ) {
            ObjectivesScreenContent(
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
        objectivesContentUiState,
        ghostNameUiActions,
        ghostResponseUiActions,
        missionWrapperActions
    )

}

@Composable
private fun ObjectivesScreenContent(
    objectivesContentUiState: ObjectivesContentUiState,
    ghostNameUiActions: GhostNameUiActions,
    ghostResponseUiActions: GhostResponseUiActions,
    missionWrapperActions: MissionWrapperActions
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

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
