package com.tritiumgaming.feature.missions.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.feature.missions.ui.components.mission.MissionWrapperActions
import com.tritiumgaming.feature.missions.ui.components.mission.MissionsContent
import com.tritiumgaming.feature.missions.ui.components.name.GhostNameContent
import com.tritiumgaming.feature.missions.ui.components.name.GhostNameUiActions
import com.tritiumgaming.feature.missions.ui.components.response.GhostResponseContent
import com.tritiumgaming.feature.missions.ui.components.response.GhostResponseUiActions

@Composable
internal fun ObjectivesContentPortrait(
    modifier: Modifier = Modifier,
    objectiveBoardContentUiState: ObjectiveBoardContentUiState,
    ghostNameUiActions: GhostNameUiActions,
    missionWrapperActions: MissionWrapperActions,
    ghostResponseUiActions: GhostResponseUiActions
) {
    val rememberScrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
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
                    .fillMaxWidth(),
                missionSpinnerUiState = objectiveBoardContentUiState.missionSpinnerUiState,
                missionWrapperActions = missionWrapperActions
            )

        }

        Column(
            modifier = Modifier
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .padding(4.dp),
                text = stringResource(R.string.objectives_title_ghost_details),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.primary,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, stepSize = 5.sp)
            )

            GhostNameContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                namesSpinnerUiState = objectiveBoardContentUiState.namesSpinnerUiState,
                ghostDetailsUiState = objectiveBoardContentUiState.ghostDetailsUiState,
                ghostNameUiActions = ghostNameUiActions
            )

            GhostResponseContent(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight(Alignment.Top),
                ghostResponseUiState = objectiveBoardContentUiState.ghostResponseUiState,
                ghostDetailsUiState = objectiveBoardContentUiState.ghostDetailsUiState,
                ghostResponseUiActions = ghostResponseUiActions
            )

        }
    }

}
