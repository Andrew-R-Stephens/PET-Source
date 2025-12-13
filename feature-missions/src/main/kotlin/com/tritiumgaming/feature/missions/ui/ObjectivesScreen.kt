package com.tritiumgaming.feature.missions.ui

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentLandscape
import com.tritiumgaming.feature.missions.ui.screens.ObjectivesContentPortrait

@Composable
fun ObjectivesScreen(
    objectivesViewModel: ObjectivesViewModel,
    difficultyUiState: DifficultyUiState
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val missionsUiState = objectivesViewModel.missionSpinnersUiState.collectAsStateWithLifecycle()

    val ghostDetailsUiState = objectivesViewModel.ghostDetailsUiState.collectAsStateWithLifecycle()

    val filteredMissions = missionsUiState.value
        .fold(objectivesViewModel.fetchAllMissions()) { missionsUi, mission ->
            missionsUi.filter { it.id != mission.mission.id }
        }

    val firstname = ghostDetailsUiState.value.firstName
    val surname = ghostDetailsUiState.value.surname
    val response = ghostDetailsUiState.value.responseState

    val firstnames = objectivesViewModel.fetchAllFirstNames().sortedBy { it.name }
    val surnames = objectivesViewModel.fetchAllSurnamesNames()

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            ObjectivesContentPortrait(
                difficultyUiState = difficultyUiState,
                missionsUiState = missionsUiState.value,
                filteredMissions = filteredMissions,
                firstnames = firstnames,
                surnames = surnames,
                firstname = firstname,
                surname = surname,
                response = response,
                onSelectFirstName = { firstname ->
                    objectivesViewModel.setGhostFirstName(firstname)
                },
                onSelectSurname = { surname ->
                    objectivesViewModel.setGhostSurname(surname)
                },
                onSelectMission = { index, mission ->
                    objectivesViewModel.selectMission(index, mission)
                },
                onChangesMissionStatus = { mission, state ->
                    objectivesViewModel.updateMissionStatus(mission, state)
                },
                onResponseChange = { response ->
                    objectivesViewModel.setGhostResponse(response)
                }
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            ObjectivesContentLandscape(
                difficultyUiState = difficultyUiState,
                missionsUiState = missionsUiState.value,
                missions = filteredMissions,
                firstnames = firstnames,
                surnames = surnames,
                firstname = firstname,
                surname = surname,
                response = response,
                onSelectFirstName = { firstname ->
                    objectivesViewModel.setGhostFirstName(firstname)
                },
                onSelectSurname = { surname ->
                    objectivesViewModel.setGhostSurname(surname)
                },
                onSelectMission = { index, mission ->
                    objectivesViewModel.selectMission(index, mission)
                },
                onChangesMissionStatus = { mission, state ->
                    objectivesViewModel.updateMissionStatus(mission, state)
                },
                onResponseChange = { response ->
                    objectivesViewModel.setGhostResponse(response)
                }
            )
        }
    }

}
