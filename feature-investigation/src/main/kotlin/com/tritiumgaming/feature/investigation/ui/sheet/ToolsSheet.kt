package com.tritiumgaming.feature.investigation.ui.sheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.composite.FingerprintDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntCooldownDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.PreventHuntIcon
import com.tritiumgaming.core.ui.mapper.toStringResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarTimer
import com.tritiumgaming.core.ui.widgets.tooltip.CommonTooltip
import com.tritiumgaming.feature.investigation.ui.tool.timers.TimerTools
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources

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
    // Link
    timersLinked: Boolean = false,
    onLinkToggle: () -> Unit = {},
    // Modifiers
    isCursedInvestigation: Boolean = false,
    difficultyTitle: DifficultyResources.DifficultyTitle = DifficultyResources.DifficultyTitle.AMATEUR,
    mapSize: MapModifierResources.MapSize = MapModifierResources.MapSize.SMALL,
    huntDuration: DifficultySettingResources.HuntDuration = DifficultySettingResources.HuntDuration.MEDIUM,
    fingerprintDuration: DifficultySettingResources.FingerprintDuration = DifficultySettingResources.FingerprintDuration.DURATION_120,
    // Colors
    notchedProgressBarUiColors: NotchedProgressBarUiColors
) {
    TimerTools(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.Top
        ) {
            val linkPainter = painterResource(R.drawable.ic_link)
            val iconColor =
                if (timersLinked) LocalPalette.current.onSurfaceVariant else LocalPalette.current.onSurface
            val lineColor =
                if (timersLinked) LocalPalette.current.onSurfaceVariant else LocalPalette.current.onSurface.copy(alpha = .3f)

            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(16.dp)
                    .padding(vertical = 12.dp)
                    .clickable { onLinkToggle() }
            ) {
                val strokeWidth = 2.dp.toPx()
                val x = size.width / 2
                val centerY = size.height / 2
                val iconSize = 14.dp.toPx()
                val gap = 4.dp.toPx()

                // Top vertical line
                drawLine(
                    lineColor,
                    Offset(x, 0f),
                    Offset(x, centerY - iconSize / 2 - gap),
                    strokeWidth
                )
                // Bottom vertical line
                drawLine(
                    lineColor,
                    Offset(x, centerY + iconSize / 2 + gap),
                    Offset(x, size.height),
                    strokeWidth
                )

                // Pointing to hunt duration
                drawLine(
                    lineColor,
                    Offset(x, 0f),
                    Offset(size.width, 0f),
                    strokeWidth
                )
                // Pointing to hunt cooldown
                drawLine(
                    lineColor,
                    Offset(x, size.height),
                    Offset(size.width, size.height),
                    strokeWidth
                )

                translate(left = x - iconSize / 2, top = centerY - iconSize / 2) {
                    with(linkPainter) {
                        draw(
                            size = Size(iconSize, iconSize),
                            colorFilter = ColorFilter.tint(iconColor)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(if (timersLinked) 0.dp else 8.dp)
            ) {
                Surface(
                    modifier = Modifier,
                    color = LocalPalette.current.surfaceContainer,
                    shape = if (timersLinked) RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp) else RoundedCornerShape(8.dp)
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
                            colors = notchedProgressBarUiColors,
                            titleContent = {
                                val rememberLazyListState = rememberScrollState()

                                Row(
                                    modifier = Modifier
                                        .weight(1f, false)
                                        .horizontalScroll(rememberLazyListState),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CommonTooltip(
                                        modifier = Modifier,
                                        tooltipText = stringResource(difficultyTitle.toStringResource())
                                    ) {
                                        Image(
                                            modifier = it.size(16.dp),
                                            painter = painterResource(R.drawable.ic_trophy),
                                            colorFilter = ColorFilter.tint(LocalPalette.current.onSurfaceVariant),
                                            contentDescription = ""
                                        )
                                    }

                                    CommonTooltip(
                                        tooltipText = stringResource(mapSize.toStringResource())
                                    ) {
                                        Image(
                                            modifier = it.size(16.dp),
                                            painter = painterResource(R.drawable.icon_nav_mapmenu2),
                                            colorFilter = ColorFilter.tint(LocalPalette.current.onSurfaceVariant),
                                            contentDescription = ""
                                        )
                                    }

                                    CommonTooltip(
                                        tooltipText = stringResource(huntDuration.toStringResource())
                                    ) {
                                        HuntDurationIcon(
                                            modifier = it.size(16.dp),
                                            colors = IconVectorColors.defaults().copy(
                                                fillColor = LocalPalette.current.onSurfaceVariant
                                            )
                                        )
                                    }

                                    if (isCursedInvestigation) {
                                        CommonTooltip(
                                            tooltipText = stringResource(R.string.tool_timer_label_cursed)
                                        ) {
                                            Image(
                                                modifier = it.size(16.dp),
                                                painter = painterResource(R.drawable.ic_map_cp_ouija),
                                                colorFilter = ColorFilter.tint(LocalPalette.current.onSurfaceVariant),
                                                contentDescription = ""
                                            )
                                        }
                                    }
                                }
                            }
                        ) { modifier ->
                            HuntDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }

                    }
                }

                if (timersLinked) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        thickness = 1.dp,
                        color = LocalPalette.current.onSurface.copy(alpha = 0.1f)
                    )
                }

                Surface(
                    modifier = Modifier,
                    color = LocalPalette.current.surfaceContainer,
                    shape = if (timersLinked) RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp) else RoundedCornerShape(8.dp)
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
                        colors = notchedProgressBarUiColors,
                        showControls = !timersLinked,
                        titleContent = {
                            if (timersLinked) {
                                CommonTooltip(
                                    tooltipText = stringResource(R.string.tool_timer_label_linked)
                                ) {
                                    Image(
                                        modifier = it.size(16.dp),
                                        painter = painterResource(R.drawable.ic_link),
                                        colorFilter = ColorFilter.tint(LocalPalette.current.onSurfaceVariant),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
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
                colors = notchedProgressBarUiColors,
                titleContent = {
                    CommonTooltip(
                        tooltipText = stringResource(fingerprintDuration.toStringResource())
                    ) {
                        FingerprintDurationIcon(
                            modifier = it.size(16.dp),
                            colors = IconVectorColors.defaults().copy(
                                fillColor = LocalPalette.current.onSurfaceVariant
                            )
                        )
                    }
                }
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
