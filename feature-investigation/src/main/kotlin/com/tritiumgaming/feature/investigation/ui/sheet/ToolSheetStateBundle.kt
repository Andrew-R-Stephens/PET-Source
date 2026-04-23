package com.tritiumgaming.feature.investigation.ui.sheet

import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureStateBundle
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureUiActions
import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.feature.investigation.ui.WeatherUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiActions
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigActionsBundle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigStateBundle
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmToolUiActions
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListUiActions
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources

internal data class ToolSheetStateBundle(
    val smudgeHuntPreventionBundle: NotchedProgressBarBundle,
    val huntDurationBundle: NotchedProgressBarBundle,
    val huntCooldownBundle: NotchedProgressBarBundle,
    val fingerprintTimerBundle: NotchedProgressBarBundle,
    val difficultyUiStateBundle: ConfigStateBundle,
    val mapUiStateBundle: ConfigStateBundle,
    val weatherUiStateBundle: ConfigStateBundle,
    val temperatureStateBundle: TemperatureStateBundle,
    val weatherUiState: WeatherUiState,
    val toolbarUiState: OperationToolbarUiState,
    val traitListUiState: TraitListUiState,
    val operationDetailsUiState: OperationDetailsUiState,
    val bpmToolUiState: BpmToolUiState,
    val sanityUiState: PlayerSanityUiState,
    val timerUiState: TimerUiState,
    val phaseUiState: OperationDetailsUiState.PhaseDetails,
)

internal data class ToolSheetActionsBundle(
    val difficultyUiActions: ConfigActionsBundle,
    val mapUiActions: ConfigActionsBundle,
    val weatherUiActions: ConfigActionsBundle,
    val traitListUiActions: TraitListUiActions,
    val bpmToolUiActions: BpmToolUiActions,
    val timerUiActions: TimerUiActions,
    val temperatureUiActions: TemperatureUiActions,
    val onSanityChange: (Float) -> Unit = {},
    val onWeatherChange: (DifficultySettingResources.Weather) -> Unit = {},
    val onUseSanityMedication: () -> Unit = {},
    val onPlayerDeath: () -> Unit = {}
)
