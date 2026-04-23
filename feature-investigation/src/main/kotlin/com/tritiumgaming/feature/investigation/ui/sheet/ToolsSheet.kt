package com.tritiumgaming.feature.investigation.ui.sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.composite.HuntCooldownDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.PreventHuntIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.feature.investigation.app.mappers.weather.toDrawable
import com.tritiumgaming.feature.investigation.ui.OperationConfigsBottomSheet
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetails
import com.tritiumgaming.feature.investigation.ui.tool.configs.DifficultyConfigComponent
import com.tritiumgaming.feature.investigation.ui.tool.configs.FuseBoxControlComponent
import com.tritiumgaming.feature.investigation.ui.tool.configs.MapConfigComponent
import com.tritiumgaming.feature.investigation.ui.tool.configs.WeatherConfigComponent
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmTool
import com.tritiumgaming.feature.investigation.ui.tool.sanity.PlayerDeathButtonComponent
import com.tritiumgaming.feature.investigation.ui.tool.sanity.SanityMedicationComponent
import com.tritiumgaming.feature.investigation.ui.tool.sanity.SanityMeterComponent
import com.tritiumgaming.feature.investigation.ui.tool.sanity.TimerComponentColumn
import com.tritiumgaming.feature.investigation.ui.tool.sanity.TimerComponentRow
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureComponent
import com.tritiumgaming.feature.investigation.ui.tool.timers.ProgressBarTimer
import com.tritiumgaming.feature.investigation.ui.tool.timers.TimerTools
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitConfig
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListItemUiColors
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

@Composable
internal fun ToolsBottomSheetComponent(
    modifier: Modifier = Modifier,
    stateBundle: ToolSheetStateBundle,
    actionsBundle: ToolSheetActionsBundle
) {

    val weatherUiState = stateBundle.weatherUiState
    val toolbarUiState = stateBundle.toolbarUiState
    val traitListUiState = stateBundle.traitListUiState
    val operationDetailsUiState = stateBundle.operationDetailsUiState
    val bpmToolUiState = stateBundle.bpmToolUiState
    val sanityUiState = stateBundle.sanityUiState
    val timerUiState = stateBundle.timerUiState
    val phaseUiState = stateBundle.phaseUiState
    val temperatureBundle = stateBundle.temperatureStateBundle
    val smudgeHuntPreventionBundle = stateBundle.smudgeHuntPreventionBundle
    val huntDurationBundle = stateBundle.huntDurationBundle
    val huntCooldownBundle = stateBundle.huntCooldownBundle
    val fingerprintTimerBundle = stateBundle.fingerprintTimerBundle
    val difficultyUiStateBundle = stateBundle.difficultyUiStateBundle
    val mapUiStateBundle = stateBundle.mapUiStateBundle
    val weatherUiStateBundle = stateBundle.weatherUiStateBundle

    val difficultyUiActions = actionsBundle.difficultyUiActions
    val mapUiActions = actionsBundle.mapUiActions
    val weatherUiActions = actionsBundle.weatherUiActions
    val traitListUiActions = actionsBundle.traitListUiActions
    val bpmToolUiActions = actionsBundle.bpmToolUiActions
    val timerUiActions = actionsBundle.timerUiActions
    val temperatureUiActions = actionsBundle.temperatureUiActions

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when (toolbarUiState.category) {
            OperationToolbarUiState.Category.TOOL_NONE -> {}
            OperationToolbarUiState.Category.TOOL_CONFIG -> {
                OperationConfigsBottomSheet(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    mapConfigComponent = { modifier ->
                        MapConfigComponent(
                            modifier = modifier,
                            bundle = mapUiStateBundle,
                            actions = mapUiActions
                        )
                    },
                    difficultyConfigComponent = { modifier ->
                        DifficultyConfigComponent(
                            modifier = modifier,
                            bundle = difficultyUiStateBundle,
                            actions = difficultyUiActions
                        )
                    },
                    weatherConfigComponent = { modifier ->
                        WeatherConfigComponent(
                            modifier = modifier,
                            icon = weatherUiState.weather.toDrawable(),
                            bundle = weatherUiStateBundle,
                            actions = weatherUiActions
                        )
                    },
                    sanityMeterComponent = { modifier ->
                        SanityMeterComponent(
                            modifier = modifier,
                            sanityUiState = sanityUiState
                        ) {
                            actionsBundle.onSanityChange(it)
                        }
                    },
                    timerComponent = { modifier ->
                        TimerComponentColumn(
                            modifier = modifier,
                            timerUiState = timerUiState,
                            timerUiActions = timerUiActions,
                            phaseUiState = phaseUiState
                        )
                    },
                    sanityMedicationComponent = { modifier ->
                        SanityMedicationComponent(
                            modifier = modifier,
                            onClick = {
                                actionsBundle.onUseSanityMedication()
                            }
                        )
                    },
                    playerDeathButtonComponent = { modifier ->
                        PlayerDeathButtonComponent(
                            modifier = modifier,
                            onClick = {
                                actionsBundle.onPlayerDeath()
                            }
                        )
                    },
                    temperatureMeterComponent = { modifier ->
                        TemperatureComponent(
                            modifier = modifier,
                            state = temperatureBundle
                        )
                    },
                    fuseBoxControlComponent = { modifier ->
                        FuseBoxControlComponent(
                            modifier = modifier,
                            state = temperatureBundle,
                            actions = temperatureUiActions
                        )
                    },
                    showTemperatureMeterComponent = weatherUiState.weather != Weather.RANDOM
                )
            }

            OperationToolbarUiState.Category.TOOL_TRAITS -> {
                TraitConfig(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxHeight(.5f),
                    state = traitListUiState,
                    actions = traitListUiActions,
                    colors = TraitListItemUiColors(
                        unselectedColor = LocalPalette.current.surfaceContainerHigh,
                        unselectedOnColor = LocalPalette.current.onSurface,
                        selectedColor = LocalPalette.current.surfaceContainerLow,
                        selectedOnColor = LocalPalette.current.onSurfaceVariant,
                    )
                )
            }

            OperationToolbarUiState.Category.TOOL_ANALYZER -> {
                OperationDetails(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    operationDetailsUiState = operationDetailsUiState
                )
            }

            OperationToolbarUiState.Category.TOOL_TIMERS -> {
                TimerTools(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(IntrinsicSize.Max),
                ) {

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            bundle = smudgeHuntPreventionBundle
                        ) { modifier ->
                            PreventHuntIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = huntDurationBundle
                        ) { modifier ->
                            HuntDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = huntCooldownBundle
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = fingerprintTimerBundle
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }
                }
            }

            OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
                BpmTool(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    state = bpmToolUiState,
                    actions = bpmToolUiActions
                )
            }

        }
    }
}

@Composable
internal fun ToolsSideSheetComponent(
    modifier: Modifier = Modifier,
    stateBundle: ToolSheetStateBundle,
    actionsBundle: ToolSheetActionsBundle
) {

    val weatherUiState = stateBundle.weatherUiState
    val toolbarUiState = stateBundle.toolbarUiState
    val traitListUiState = stateBundle.traitListUiState
    val operationDetailsUiState = stateBundle.operationDetailsUiState
    val bpmToolUiState = stateBundle.bpmToolUiState
    val sanityUiState = stateBundle.sanityUiState
    val timerUiState = stateBundle.timerUiState
    val phaseUiState = stateBundle.phaseUiState
    val temperatureBundle = stateBundle.temperatureStateBundle
    val smudgeHuntPreventionBundle = stateBundle.smudgeHuntPreventionBundle
    val huntDurationBundle = stateBundle.huntDurationBundle
    val huntCooldownBundle = stateBundle.huntCooldownBundle
    val fingerprintTimerBundle = stateBundle.fingerprintTimerBundle
    val difficultyUiStateBundle = stateBundle.difficultyUiStateBundle
    val mapUiStateBundle = stateBundle.mapUiStateBundle
    val weatherUiStateBundle = stateBundle.weatherUiStateBundle

    val difficultyUiActions = actionsBundle.difficultyUiActions
    val mapUiActions = actionsBundle.mapUiActions
    val weatherUiActions = actionsBundle.weatherUiActions
    val traitListUiActions = actionsBundle.traitListUiActions
    val bpmToolUiActions = actionsBundle.bpmToolUiActions
    val timerUiActions = actionsBundle.timerUiActions
    val temperatureUiActions = actionsBundle.temperatureUiActions

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when (toolbarUiState.category) {
            OperationToolbarUiState.Category.TOOL_NONE -> {}
            OperationToolbarUiState.Category.TOOL_CONFIG -> {

                OperationConfigsSideSheet(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    sanityMedicationComponent = { modifier ->
                        SanityMedicationComponent(
                            modifier = modifier,
                            onClick = {
                                actionsBundle.onUseSanityMedication()
                            }
                        )
                    },
                    mapConfigComponent = { modifier ->
                        MapConfigComponent(
                            modifier = modifier,
                            bundle = mapUiStateBundle,
                            actions = mapUiActions
                        )
                    },
                    difficultyConfigComponent = { modifier ->
                        DifficultyConfigComponent(
                            modifier = modifier,
                            bundle = difficultyUiStateBundle,
                            actions = difficultyUiActions
                        )
                    },
                    weatherConfigComponent = { modifier ->
                        WeatherConfigComponent(
                            modifier = modifier,
                            icon = R.drawable.icon_cp_tarot_moon,
                            bundle = weatherUiStateBundle,
                            actions = weatherUiActions
                        )
                    },
                    sanityMeterComponent = { modifier ->
                        SanityMeterComponent(
                            modifier = modifier,
                            sanityUiState = sanityUiState
                        ) {
                            actionsBundle.onSanityChange(it)
                        }
                    },
                    timerComponent = { modifier ->
                        TimerComponentRow(
                            modifier = modifier,
                            timerUiState = timerUiState,
                            timerUiActions = timerUiActions,
                            phaseUiState = phaseUiState
                        )
                    }
                )
            }

            OperationToolbarUiState.Category.TOOL_TRAITS -> {
                TraitConfig(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxHeight(),
                    state = traitListUiState,
                    actions = traitListUiActions,
                    colors = TraitListItemUiColors(
                        unselectedColor = LocalPalette.current.surfaceContainerHigh,
                        unselectedOnColor = LocalPalette.current.onSurface,
                        selectedColor = LocalPalette.current.surfaceContainerLow,
                        selectedOnColor = LocalPalette.current.onSurfaceVariant,
                    )
                )
            }

            OperationToolbarUiState.Category.TOOL_ANALYZER -> {
                OperationDetails(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    operationDetailsUiState = operationDetailsUiState
                )
            }

            OperationToolbarUiState.Category.TOOL_TIMERS -> {
                TimerTools(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(IntrinsicSize.Max),
                ) {

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            bundle = smudgeHuntPreventionBundle
                        ) { modifier ->
                            PreventHuntIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = huntDurationBundle
                        ) { modifier ->
                            HuntDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = huntCooldownBundle
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = fingerprintTimerBundle
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }
                }
            }

            OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
                BpmTool(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    state = bpmToolUiState,
                    actions = bpmToolUiActions
                )
            }

        }
    }
}

@Composable
fun OperationConfigsSideSheet(
    modifier: Modifier = Modifier,
    sanityMedicationComponent: @Composable (Modifier) -> Unit = {},
    timerComponent: @Composable (Modifier) -> Unit = {},
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    weatherConfigComponent: @Composable (Modifier) -> Unit = {},
    temperatureMeterComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = "Operation Config".uppercase(),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                fontSize = 18.sp,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )
        }

        Surface(
            modifier = Modifier,
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 2.dp,
                color = LocalPalette.current.surfaceContainerLow
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {

                mapConfigComponent(
                    Modifier
                        .fillMaxWidth()
                )

                difficultyConfigComponent(
                    Modifier
                        .fillMaxWidth()
                )

            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 2.dp,
                color = LocalPalette.current.surfaceContainerLow
            )
        ) {
            Box(
                Modifier
                    .padding(8.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                timerComponent(
                    Modifier
                        .fillMaxWidth()
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                sanityMeterComponent(
                    Modifier
                        .height(48.dp)
                        .weight(1f)
                        .padding(8.dp)
                )
            }

            Surface(
                modifier = Modifier,
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    sanityMedicationComponent(
                        Modifier
                            .size(48.dp)
                    )
                }
            }

        }

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start
                ) {

                    weatherConfigComponent(
                        Modifier
                            .fillMaxWidth()
                    )

                }
            }

            //TODO Temperature Meter
            Surface(
                modifier = Modifier
                    .fillMaxHeight(),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                Box(
                    Modifier
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    temperatureMeterComponent(
                        Modifier
                            .width(IntrinsicSize.Min)
                    )
                }
            }

        }

    }

}
