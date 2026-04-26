package com.tritiumgaming.feature.investigation.ui.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.composite.HuntCooldownDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.PreventHuntIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.feature.investigation.app.mappers.weather.toDrawable
import com.tritiumgaming.feature.investigation.ui.OperationConfigsBottomSheet
import com.tritiumgaming.feature.investigation.ui.OperationConfigsSideSheet
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetails
import com.tritiumgaming.feature.investigation.ui.tool.configs.DifficultyConfigControl
import com.tritiumgaming.feature.investigation.ui.tool.configs.FuseBoxButton
import com.tritiumgaming.feature.investigation.ui.tool.configs.MapConfigControl
import com.tritiumgaming.feature.investigation.ui.tool.configs.WeatherConfigComponent
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmTool
import com.tritiumgaming.feature.investigation.ui.tool.sanity.PlayerDeathButton
import com.tritiumgaming.feature.investigation.ui.tool.sanity.SanityMedicationButton
import com.tritiumgaming.feature.investigation.ui.tool.sanity.SanityMeter
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerColumn
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerRow
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
    val timerUiState = stateBundle.operationTimerUiState
    val phaseUiState = stateBundle.phaseUiState
    val temperatureBundle = stateBundle.temperatureStateBundle
    val fuseBoxUiState = stateBundle.fuseBoxUiState
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
    val fuseBoxUiActions = actionsBundle.fuseBoxUiActions

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
                        MapConfigControl(
                            modifier = modifier,
                            bundle = mapUiStateBundle,
                            actions = mapUiActions
                        )
                    },
                    difficultyConfigComponent = { modifier ->
                        DifficultyConfigControl(
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
                        SanityMeter(
                            modifier = modifier,
                            sanityUiState = sanityUiState
                        ) {
                            actionsBundle.onSanityChange(it)
                        }
                    },
                    timerComponent = { modifier ->
                        OperationTimerColumn(
                            modifier = modifier,
                            operationTimerUiState = timerUiState,
                            timerUiActions = timerUiActions,
                            phaseUiState = phaseUiState
                        )
                    },
                    sanityMedicationComponent = { modifier ->
                        SanityMedicationButton(
                            modifier = modifier,
                            onClick = {
                                actionsBundle.onUseSanityMedication()
                            }
                        )
                    },
                    playerDeathButtonComponent = { modifier ->
                        PlayerDeathButton(
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
                        FuseBoxButton(
                            modifier = modifier,
                            state = fuseBoxUiState,
                            actions = fuseBoxUiActions
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
    val timerUiState = stateBundle.operationTimerUiState
    val phaseUiState = stateBundle.phaseUiState
    val fuseBoxUiState = stateBundle.fuseBoxUiState
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
    val fuseBoxUiActions = actionsBundle.fuseBoxUiActions

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
                        SanityMedicationButton(
                            modifier = modifier,
                            onClick = {
                                actionsBundle.onUseSanityMedication()
                            }
                        )
                    },
                    mapConfigComponent = { modifier ->
                        MapConfigControl(
                            modifier = modifier,
                            bundle = mapUiStateBundle,
                            actions = mapUiActions
                        )
                    },
                    difficultyConfigComponent = { modifier ->
                        DifficultyConfigControl(
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
                        SanityMeter(
                            modifier = modifier,
                            sanityUiState = sanityUiState
                        ) {
                            actionsBundle.onSanityChange(it)
                        }
                    },
                    timerComponent = { modifier ->
                        OperationTimerRow(
                            modifier = modifier,
                            operationTimerUiState = timerUiState,
                            timerUiActions = timerUiActions,
                            phaseUiState = phaseUiState
                        )
                    },
                    playerDeathButtonComponent = { modifier ->
                        PlayerDeathButton(
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
                        FuseBoxButton(
                            modifier = modifier,
                            state = fuseBoxUiState,
                            actions = fuseBoxUiActions
                        )
                    },
                    showTemperatureMeterComponent = weatherUiState.weather != Weather.RANDOM

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
