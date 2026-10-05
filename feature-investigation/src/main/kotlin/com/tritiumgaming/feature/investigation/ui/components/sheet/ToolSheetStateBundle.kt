package com.tritiumgaming.feature.investigation.ui.components.sheet

import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import com.tritiumgaming.feature.investigation.ui.components.common.digitaltimer.TimerUiActions
import com.tritiumgaming.feature.investigation.ui.components.common.operationconfig.ConfigActionsBundle
import com.tritiumgaming.feature.investigation.ui.components.common.operationconfig.ConfigStateBundle
import com.tritiumgaming.feature.investigation.ui.components.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.components.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.components.tool.configs.FuseBoxUiActions
import com.tritiumgaming.feature.investigation.ui.components.tool.configs.FuseBoxUiState
import com.tritiumgaming.feature.investigation.ui.components.tool.configs.WeatherUiState
import com.tritiumgaming.feature.investigation.ui.components.tool.footstep.BpmToolUiActions
import com.tritiumgaming.feature.investigation.ui.components.tool.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.components.tool.operationtimer.OperationTimerUiState
import com.tritiumgaming.feature.investigation.ui.components.tool.phase.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.components.tool.temperature.TemperatureStateBundle
import com.tritiumgaming.feature.investigation.ui.components.tool.traits.TraitListUiActions
import com.tritiumgaming.feature.investigation.ui.components.tool.traits.TraitListUiState
import com.tritiumgaming.feature.investigation.ui.components.toolbar.operation.OperationToolbarUiState
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources

internal data class ToolSheetStateBundle(
    val smudgeHuntPreventionBundle: NotchedProgressBarBundle,
    val huntDurationBundle: NotchedProgressBarBundle,
    val huntCooldownBundle: NotchedProgressBarBundle,
    val fingerprintTimerBundle: NotchedProgressBarBundle,
    val difficultyUiStateBundle: ConfigStateBundle,
    val mapUiStateBundle: ConfigStateBundle,
    val weatherUiStateBundle: ConfigStateBundle,
    val temperatureStateBundle: TemperatureStateBundle,
    val fuseBoxUiState: FuseBoxUiState,
    val weatherUiState: WeatherUiState,
    val toolbarUiState: OperationToolbarUiState,
    val traitListUiState: TraitListUiState,
    val operationDetailsUiState: OperationDetailsUiState,
    val bpmToolUiState: BpmToolUiState,
    val sanityUiState: PlayerSanityUiState,
    val operationTimerUiState: OperationTimerUiState,
    val phaseUiState: PhaseUiState,
)

internal data class ToolSheetActionsBundle(
    val difficultyUiActions: ConfigActionsBundle,
    val mapUiActions: ConfigActionsBundle,
    val weatherUiActions: ConfigActionsBundle,
    val traitListUiActions: TraitListUiActions,
    val bpmToolUiActions: BpmToolUiActions,
    val timerUiActions: TimerUiActions,
    val fuseBoxUiActions: FuseBoxUiActions,
    val onSanityChange: (Float) -> Unit = {},
    val onWeatherChange: (DifficultySettingResources.Weather) -> Unit = {},
    val onUseSanityMedication: () -> Unit = {},
    val onPlayerDeath: () -> Unit = {}
)
