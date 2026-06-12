package com.tritiumgaming.feature.investigation.ui.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.impl.composite.FingerprintDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntCooldownDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.PreventHuntIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarTimer
import com.tritiumgaming.feature.investigation.ui.tool.timers.TimerTools
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState

@Composable
internal fun ToolsBottomSheetComponent(
    modifier: Modifier = Modifier,
    toolbarCategory: OperationToolbarUiState.Category,
    configComponent: @Composable (Modifier) -> Unit,
    traitsComponent: @Composable (Modifier) -> Unit,
    analyzerComponent: @Composable (Modifier) -> Unit,
    timersComponent: @Composable (Modifier) -> Unit,
    footstepComponent: @Composable (Modifier) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when (toolbarCategory) {
            OperationToolbarUiState.Category.TOOL_NONE -> {}
            OperationToolbarUiState.Category.TOOL_CONFIG -> {
                configComponent(
                    Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max)
                )
            }

            OperationToolbarUiState.Category.TOOL_TRAITS -> {
                traitsComponent(
                    Modifier
                        .padding(top = 8.dp)
                        .fillMaxHeight(.5f)
                )
            }

            OperationToolbarUiState.Category.TOOL_ANALYZER -> {
                analyzerComponent(
                    Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max)
                )
            }

            OperationToolbarUiState.Category.TOOL_TIMERS -> {
                timersComponent(
                    Modifier
                        .padding(8.dp)
                        .height(IntrinsicSize.Max)
                )
            }

            OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
                footstepComponent(
                    Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max)
                )
            }
        }
    }
}

@Composable
internal fun ToolsSideSheetComponent(
    modifier: Modifier = Modifier,
    toolbarCategory: OperationToolbarUiState.Category,
    configComponent: @Composable (Modifier) -> Unit,
    traitsComponent: @Composable (Modifier) -> Unit,
    analyzerComponent: @Composable (Modifier) -> Unit,
    timersComponent: @Composable (Modifier) -> Unit,
    footstepComponent: @Composable (Modifier) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when (toolbarCategory) {
            OperationToolbarUiState.Category.TOOL_NONE -> {}
            OperationToolbarUiState.Category.TOOL_CONFIG -> {
                configComponent(
                    Modifier
                        .padding(top = 8.dp)
                        .verticalScroll(rememberScrollState())
                )
            }

            OperationToolbarUiState.Category.TOOL_TRAITS -> {
                traitsComponent(
                    Modifier
                        .padding(top = 8.dp)
                        .fillMaxHeight()
                )
            }

            OperationToolbarUiState.Category.TOOL_ANALYZER -> {
                analyzerComponent(
                    Modifier
                        .padding(top = 8.dp)
                )
            }

            OperationToolbarUiState.Category.TOOL_TIMERS -> {
                timersComponent(
                    Modifier
                        .padding(8.dp)
                        .height(IntrinsicSize.Max)
                )
            }

            OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
                footstepComponent(
                    Modifier
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState())
                )
            }
        }
    }
}

@Composable
internal fun ToolsTimerComponent(
    modifier: Modifier = Modifier,
    // Timers state SMUDGE
    smudgeHuntPreventionTitle: String,
    smudgeHuntPreventionMax: Long,
    smudgeHuntPreventionRemaining: Long,
    smudgeHuntPreventionTimeText: String,
    smudgeHuntPreventionRunning: Boolean,
    onSmudgeToggle: () -> Unit,
    smudgeNotches: List<ProgressBarNotch>,
    // Timers state HUNT DURATION
    huntDurationTitle: String,
    huntDurationMax: Long,
    huntDurationRemaining: Long,
    huntDurationTimeText: String,
    huntDurationRunning: Boolean,
    onHuntDurationToggle: () -> Unit,
    huntDurationNotches: List<ProgressBarNotch>,
    // Timers state HUNT COOLDOWN
    huntCooldownTitle: String,
    huntCooldownMax: Long,
    huntCooldownRemaining: Long,
    huntCooldownTimeText: String,
    huntCooldownRunning: Boolean,
    onHuntCooldownToggle: () -> Unit,
    huntCooldownNotches: List<ProgressBarNotch>,
    // Timers state FINGERPRINT
    fingerprintTimerTitle: String,
    fingerprintTimerMax: Long,
    fingerprintTimerRemaining: Long,
    fingerprintTimerTimeText: String,
    fingerprintTimerRunning: Boolean,
    onFingerprintToggle: () -> Unit,
    fingerprintNotches: List<ProgressBarNotch>,
    // Colors
    notchedProgressBarUiColors: NotchedProgressBarUiColors
) {
    TimerTools(
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier,
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp)
        ) {
            NotchedProgressBarTimer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                title = smudgeHuntPreventionTitle,
                max = smudgeHuntPreventionMax,
                remaining = smudgeHuntPreventionRemaining,
                timeText = smudgeHuntPreventionTimeText,
                running = smudgeHuntPreventionRunning,
                onToggle = onSmudgeToggle,
                notches = smudgeNotches,
                colors = notchedProgressBarUiColors
            ) { modifier ->
                PreventHuntIcon(
                    modifier = modifier,
                    colors = IconVectorColors.defaults().copy(
                        fillColor = LocalPalette.current.onSurface
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier,
                    color = LocalPalette.current.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        NotchedProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            title = huntDurationTitle,
                            max = huntDurationMax,
                            remaining = huntDurationRemaining,
                            timeText = huntDurationTimeText,
                            running = huntDurationRunning,
                            onToggle = onHuntDurationToggle,
                            notches = huntDurationNotches,
                            colors = notchedProgressBarUiColors
                        ) { modifier ->
                            HuntDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }

                        /*Checkbox(
                            modifier = Modifier,
                            checked = false,
                            onCheckedChange = {},
                            colors = CheckboxDefaults.colors(),
                        )*/
                    }
                }

                Surface(
                    modifier = Modifier,
                    color = LocalPalette.current.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    NotchedProgressBarTimer(
                        modifier = Modifier
                            .padding(8.dp),
                        title = huntCooldownTitle,
                        max = huntCooldownMax,
                        remaining = huntCooldownRemaining,
                        timeText = huntCooldownTimeText,
                        running = huntCooldownRunning,
                        onToggle = onHuntCooldownToggle,
                        notches = huntCooldownNotches,
                        colors = notchedProgressBarUiColors
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

        Surface(
            modifier = Modifier,
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp)
        ) {
            NotchedProgressBarTimer(
                modifier = Modifier
                    .padding(8.dp),
                title = fingerprintTimerTitle,
                max = fingerprintTimerMax,
                remaining = fingerprintTimerRemaining,
                timeText = fingerprintTimerTimeText,
                running = fingerprintTimerRunning,
                onToggle = onFingerprintToggle,
                notches = fingerprintNotches,
                colors = notchedProgressBarUiColors
            ) { modifier ->
                FingerprintDurationIcon(
                    modifier = modifier,
                    colors = IconVectorColors.defaults().copy(
                        fillColor = LocalPalette.current.onSurface
                    )
                )
            }
        }
    }
}
