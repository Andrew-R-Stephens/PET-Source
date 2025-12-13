package com.tritiumgaming.feature.missions.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.missions.ui.DifficultyUiState
import com.tritiumgaming.feature.missions.ui.ObjectivesViewModel
import com.tritiumgaming.feature.missions.ui.Response
import com.tritiumgaming.shared.data.ghostname.model.GhostName
import com.tritiumgaming.shared.data.mission.model.Mission
import com.tritiumgaming.feature.missions.ui.components.GhostNameContent
import com.tritiumgaming.feature.missions.ui.components.GhostResponseContent
import com.tritiumgaming.feature.missions.ui.components.MissionsContent

@Composable
internal fun ObjectivesContentPortrait(
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
