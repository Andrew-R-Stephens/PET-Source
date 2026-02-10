package com.tritiumgaming.feature.missions.ui.components.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.other.PETImageButton
import com.tritiumgaming.core.ui.widgets.other.PETImageButtonType
import com.tritiumgaming.feature.missions.app.mappers.mission.toStringResource
import com.tritiumgaming.shared.data.mission.model.Mission

@Composable
fun MissionsContent(
    modifier: Modifier = Modifier,
    missionSpinnerUiState: MissionSpinnerUiState,
    missionWrapperActions: MissionWrapperActions
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        color = LocalPalette.current.surfaceContainer
    ) {

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            missionSpinnerUiState.selectedMissions.forEachIndexed { index, _ ->
                MissionWrapper(
                    modifier = Modifier,
                    missionSpinnerUiState = missionSpinnerUiState,
                    missionWrapperActions = missionWrapperActions,
                    index = index,
                    title = "${stringResource(R.string.objectives_title_optional_objective)} ${index + 1}"
                )
            }
        }

    }

}

data class MissionWrapperActions(
    val onSelectMission: (Int, Mission) -> Unit = { _, _ ->},
    val onChangeMissionStatus: (Mission, Boolean) -> Unit = { _, _ ->}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionWrapper(
    modifier: Modifier = Modifier,
    missionSpinnerUiState: MissionSpinnerUiState,
    missionWrapperActions: MissionWrapperActions,
    index: Int = 0,
    title: String,
) {

    val selectedMission = missionSpinnerUiState.selectedMissions[index]
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
            color = LocalPalette.current.primary,
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
                            .menuAnchor(
                                type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            )
                            .padding(horizontal = 4.dp),
                        value = stringResource(
                            selectedMission.mission.content.toStringResource()),
                        textStyle = LocalTypography.current.quaternary.regular.copy(
                            color = LocalPalette.current.onSurface,
                            fontSize = 14.sp
                        ),
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(24.dp)
                                    .wrapContentHeight(),
                                text = title,
                                style = LocalTypography.current.quaternary.regular,
                                color = LocalPalette.current.onSurface.copy(alpha = 0.5f),
                                fontSize = 14.sp
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        maxLines = 2,
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedTrailingIconColor = LocalPalette.current.onSurface,
                            focusedTrailingIconColor = LocalPalette.current.onSurface
                        ),
                        onValueChange = {},
                        readOnly = true
                    )

                    if(selectedMission.status) {
                        Image(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .height(24.dp)
                                .fillMaxWidth(),
                            painter = painterResource(R.drawable.icon_strikethrough_1),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(LocalPalette.current.primary),
                            contentScale = ContentScale.FillBounds
                        )
                    }

                }

                ExposedDropdownMenu(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = LocalPalette.current.surfaceContainer,
                    shape = RoundedCornerShape(
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    ),
                    scrollState = rememberScrollState(),
                    matchAnchorWidth = true,

                ) {

                    missionSpinnerUiState.availableMissions.forEach { mission ->
                        DropdownMenuItem(
                            text =  {
                                Text(
                                    text = stringResource(mission.content.toStringResource()),
                                    style = LocalTypography.current.quaternary.regular,
                                    color = LocalPalette.current.onSurface,
                                    fontSize = 18.sp
                                )
                            },
                            colors = MenuDefaults.itemColors().copy(
                                textColor = LocalPalette.current.onSurface,
                            ),
                            contentPadding = PaddingValues.Zero,
                            onClick = {
                                expanded = false
                                missionWrapperActions.onSelectMission(index, mission)
                            },
                        )
                    }

                }
            }

            PETImageButton(
                modifier = Modifier
                    .size(48.dp)
                    .clickable(onClick = {
                        missionWrapperActions.onChangeMissionStatus(
                            selectedMission.mission, !selectedMission.status
                        )
                    }),
                type =
                    if(!selectedMission.status) PETImageButtonType.CONFIRM
                    else PETImageButtonType.CANCEL
            )

        }

    }

}
