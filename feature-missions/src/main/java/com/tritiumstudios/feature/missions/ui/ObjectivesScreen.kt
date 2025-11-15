package com.tritiumstudios.feature.missions.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.shared.operation.domain.ghostname.model.GhostName
import com.tritiumgaming.shared.operation.domain.mission.model.Mission
import com.tritiumstudios.feature.missions.ui.components.GhostNameContent
import com.tritiumstudios.feature.missions.ui.components.GhostResponseContent
import com.tritiumstudios.feature.missions.ui.components.MissionsContent

@Composable
fun ObjectivesScreen(
    navController: NavHostController = rememberNavController(),
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

@Composable
private fun ObjectivesContentPortrait(
    modifier: Modifier = Modifier,
    missionsUiState: List<ObjectivesViewModel.MissionSpinnerUiState>,
    difficultyUiState: DifficultyUiState,
    filteredMissions: List<Mission>,
    firstnames: List<GhostName>,
    surnames: List<GhostName>,
    firstname: GhostName? = null,
    surname: GhostName? = null,
    response: Response,
    onSelectFirstName: (ghostName: GhostName) -> Unit,
    onSelectSurname: (ghostName: GhostName) -> Unit,
    onSelectMission: (Int, Mission) -> Unit,
    onChangesMissionStatus: (Mission, Boolean) -> Unit,
    onResponseChange: (Response) -> Unit,
) {
    val rememberScrollState = rememberScrollState()

    Column(
        modifier = modifier
            .wrapContentHeight()
            .verticalScroll(state = rememberScrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(8.dp)
        ) {

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp),
                text = stringResource(R.string.objectives_title_optional_objectives),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.primary,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, stepSize = 5.sp)
            )

            MissionsContent(
                modifier = Modifier
                    .wrapContentHeight(Alignment.Top),
                missionsUiState = missionsUiState,
                filteredMissions = filteredMissions,
                onSelectMission = { index, mission ->
                    onSelectMission(index, mission)
                },
                onChangeMissionStatus = { mission, state ->
                    onChangesMissionStatus(mission, state)
                }
            )

        }

        Column(
            modifier = Modifier
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp),
                text = stringResource(R.string.objectives_title_investigation_debrief),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.primary,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, stepSize = 5.sp)
            )

            GhostNameContent(
                firstnames = firstnames,
                surnames = surnames,
                firstname = firstname,
                surname = surname,
                onSelectFirstName = { firstname ->
                    onSelectFirstName(firstname)
                },
                onSelectSurname = { surname ->
                    onSelectSurname(surname)
                }
            )

            GhostResponseContent(
                modifier = Modifier
                    .wrapContentHeight(Alignment.Top),
                difficultyUiState = difficultyUiState,
                response = response,
                onResponseChange = { response ->
                    onResponseChange(response)
                }
            )

        }
    }

}

@Composable
private fun ObjectivesContentLandscape(
    modifier: Modifier = Modifier,
    missionsUiState: List<ObjectivesViewModel.MissionSpinnerUiState>,
    difficultyUiState: DifficultyUiState,
    missions: List<Mission>,
    firstnames: List<GhostName>,
    surnames: List<GhostName>,
    firstname: GhostName? = null,
    surname: GhostName? = null,
    response: Response,
    onSelectFirstName: (ghostName: GhostName) -> Unit,
    onSelectSurname: (ghostName: GhostName) -> Unit,
    onSelectMission: (Int, Mission) -> Unit,
    onChangesMissionStatus: (Mission, Boolean) -> Unit,
    onResponseChange: (Response) -> Unit
) {
    val rememberScrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
    ) {

        Column(
            modifier = Modifier
                .weight(1f, false)
                .wrapContentHeight()
                .verticalScroll(state = rememberScrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp),
                text = stringResource(R.string.objectives_title_optional_objectives),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.primary,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, stepSize = 5.sp)
            )

            MissionsContent(
                modifier = Modifier
                    .wrapContentHeight(Alignment.Top)
                    .padding(8.dp),
                missionsUiState = missionsUiState,
                filteredMissions = missions,
                onSelectMission = { index, mission ->
                    onSelectMission(index, mission)
                },
                onChangeMissionStatus = { mission, state ->
                    onChangesMissionStatus(mission, state)
                }
            )

        }

        Column(
            modifier = Modifier
                .weight(1f, false)
                .wrapContentHeight()
                .verticalScroll(state = rememberScrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp),
                text = stringResource(R.string.objectives_title_investigation_debrief),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.primary,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, stepSize = 5.sp)
            )

            GhostNameContent(
                firstnames = firstnames,
                surnames = surnames,
                firstname = firstname,
                surname = surname,
                onSelectFirstName = { firstname ->
                    onSelectFirstName(firstname)
                },
                onSelectSurname = { surname ->
                    onSelectSurname(surname)
                }
            )

            GhostResponseContent(
                modifier = Modifier
                    .wrapContentHeight(Alignment.Top),
                difficultyUiState = difficultyUiState,
                response = response,
                onResponseChange = { response ->
                    onResponseChange(response)
                }
            )

        }
    }

}
