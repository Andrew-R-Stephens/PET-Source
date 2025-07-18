package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.ObjectivesViewModel

@Composable
fun MissionsContent(
    modifier: Modifier = Modifier,
    objectivesViewModel: ObjectivesViewModel
) {
    val missionsUiState = objectivesViewModel.missionSpinnersUiState.collectAsStateWithLifecycle()

    val filteredMissions = missionsUiState.value
        .fold(objectivesViewModel.fetchAllMissions()) { missionsUi, mission ->
            missionsUi.filter { it.id != mission.mission.id }
        }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        color = LocalPalette.current.surface.onColor
    ) {

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            missionsUiState.value.forEachIndexed { index, missionUiState ->
                MissionWrapper(
                    modifier = Modifier,
                    objectivesViewModel = objectivesViewModel,
                    state = missionsUiState.value[index],
                    dropdownList = filteredMissions,
                    index = index,
                    title =
                        "${stringResource(R.string.objectives_title_optional_objective)} ${index + 1}"
                )
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionWrapper(
    modifier: Modifier = Modifier,
    objectivesViewModel: ObjectivesViewModel,
    state: ObjectivesViewModel.MissionSpinnerUiState,
    dropdownList: List<Mission>,
    index: Int = 0,
    title: String
) {

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {

        Text(
            text = title,
            fontSize = 16.sp,
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

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                            .padding(horizontal = 4.dp),
                        value = stringResource(state.mission.content.toStringResource()),
                        textStyle = LocalTypography.current.quaternary.regular.copy(
                            color = LocalPalette.current.textFamily.body,
                            fontSize = 18.sp
                        ),
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(24.dp)
                                    .wrapContentHeight(),
                                text = title,
                                style = LocalTypography.current.quaternary.regular,
                                color = LocalPalette.current.textFamily.body.copy(alpha = 0.5f),
                                fontSize = 18.sp
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        maxLines = 1,
                        singleLine = true,
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                        onValueChange = {},
                        readOnly = true,
                    )

                    if(state.status) {
                        Image(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .height(24.dp)
                                .fillMaxWidth(),
                            painter = painterResource(R.drawable.icon_strikethrough_1),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.primary),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                }

                ExposedDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = LocalPalette.current.surface.onColor,
                    shape = RoundedCornerShape(
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    ),
                    scrollState = rememberScrollState(),
                    matchAnchorWidth = true,
                ) {

                    dropdownList.forEach { it ->
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
                            state.mission,
                            !state.status
                        )
                    }),
                type =
                    if(!state.status) PETImageButtonType.CONFIRM
                    else PETImageButtonType.CANCEL
            )

        }

    }

}
