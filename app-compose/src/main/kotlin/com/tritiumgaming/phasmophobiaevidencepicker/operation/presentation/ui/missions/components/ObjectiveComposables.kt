package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.ObjectivesViewModel

@Composable
fun ObjectiveContent(
    objectivesViewModel: ObjectivesViewModel
) {

    MissionsContent(
        objectivesViewModel = objectivesViewModel
    )

}

@Composable
fun MissionsContent(
    objectivesViewModel: ObjectivesViewModel
) {
    val missionsUiState = objectivesViewModel.missionSpinnersUiState.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        missionsUiState.value.forEachIndexed { index, missionUiState ->
            MissionWrapper(
                objectivesViewModel = objectivesViewModel,
                index = index,
                title =
                    "${stringResource(R.string.objectives_title_optional_objective)} ${index + 1}"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionWrapper(
    modifier: Modifier = Modifier,
    objectivesViewModel: ObjectivesViewModel,
    index: Int = 0,
    title: String
) {
    val missionsUiState = objectivesViewModel.missionSpinnersUiState.collectAsStateWithLifecycle()
    val missionUiState = missionsUiState.value[index]

    val filteredMissions = missionsUiState.value
        .fold(objectivesViewModel.fetchAllMissions()) { missionsUi, mission ->
            missionsUi.filter { it.id != mission.mission.id }
        }

    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        color = LocalPalette.current.surface.onColor
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {

            Text(
                text = title,
                fontSize = 24.sp,
                style = LocalTypography.current.primary.bold,
                color = LocalPalette.current.textFamily.primary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .wrapContentHeight()
            ) {

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                            .wrapContentHeight(),
                        text = stringResource(missionUiState.mission.content.toStringResource()),
                        style = LocalTypography.current.quaternary.regular,
                        color = LocalPalette.current.textFamily.body,
                        fontSize = 18.sp
                    )

                    ExposedDropdownMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(20.dp),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        containerColor = LocalPalette.current.surface.onColor.copy(
                            alpha = 0.95f
                        ),
                        shape = RoundedCornerShape(
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        ),
                        scrollState = rememberScrollState(),
                        matchAnchorWidth = true,
                    ) {

                        filteredMissions.forEach { it ->
                            DropdownMenuItem(
                                text =  {
                                    Text(
                                        text = stringResource(it.content.toStringResource()),
                                        style = LocalTypography.current.quaternary.regular,
                                        color = LocalPalette.current.textFamily.body,
                                        fontSize = 18.sp
                                    )
                                },
                                colors = MenuDefaults.itemColors().copy(
                                    textColor = LocalPalette.current.textFamily.body,
                                ),
                                onClick = {
                                    expanded = false
                                    objectivesViewModel.selectMission(index, it)
                                },
                            )
                        }

                    }
                }

                PETImageButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = {
                            objectivesViewModel.updateMissionStatus(
                                missionsUiState.value[index].mission,
                                !missionsUiState.value[index].status)
                        }),
                    type =
                        if(!missionsUiState.value[index].status) PETImageButtonType.CONFIRM
                        else PETImageButtonType.CANCEL
                )

            }

        }

    }

}

@Preview
@Composable
private fun ObjectiveSelectorPreview() {

    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {

        Column {

            Spacer(modifier = Modifier.height(64.dp))

            ObjectiveContent(
                objectivesViewModel = viewModel( factory = ObjectivesViewModel.Factory )
            )

        }
    }

}
