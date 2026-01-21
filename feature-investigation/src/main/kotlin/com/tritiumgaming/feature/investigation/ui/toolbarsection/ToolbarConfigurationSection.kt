package com.tritiumgaming.feature.investigation.ui.toolbarsection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.button.CollapseButton
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.getExitVector
import com.tritiumgaming.core.ui.vector.getGearVector
import com.tritiumgaming.core.ui.vector.getInfoVector
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.common.analysis.OperationDetails
import com.tritiumgaming.feature.investigation.ui.common.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerToggleButton
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiState
import com.tritiumgaming.feature.investigation.ui.common.footstep.FootstepVisualizer
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.OperationConfigCarousel
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.difficulty.DifficultyUiState
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.map.MapUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.SanityMeter
import com.tritiumgaming.feature.investigation.ui.toolbar.InvestigationToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.ResetButton
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarItem
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.component.OperationConfigUiActions
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import kotlin.time.Duration.Companion.seconds


@Composable
fun ColumnScope.ToolbarConfigurationSection(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState,
    mapUiState: MapUiState,
    difficultyUiState: DifficultyUiState,
    sanityUiState: PlayerSanityUiState,
    operationConfigUiActions: OperationConfigUiActions
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {

        OperationConfigCarousel(
            modifier = Modifier,
            primaryIcon = R.drawable.icon_nav_mapmenu2,
            label = stringResource(mapUiState.name.toStringResource(
                SimpleMapResources.MapTitleLength.ABBREVIATED)),
            textStyle = LocalTypography.current.secondary.regular,
            color = LocalPalette.current.onSurface,
            iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            onClickLeft = { operationConfigUiActions.onMapLeftClick() },
            onClickRight = { operationConfigUiActions.onMapRightClick() }
        )

        OperationConfigCarousel(
            modifier = Modifier,
            primaryIcon = R.drawable.ic_puzzle,
            label = stringResource(difficultyUiState.name.toStringResource()),
            textStyle = LocalTypography.current.secondary.regular,
            color = LocalPalette.current.onSurface,
            iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            onClickLeft = { operationConfigUiActions.onDifficultyLeftClick() },
            onClickRight = { operationConfigUiActions.onDifficultyRightClick() }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SanityMeter(
                modifier = Modifier
                    .size(64.dp),
                sanityUiState = sanityUiState
            )
            
            Column(
                modifier = Modifier
                    .weight(1f, false)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Remaining Time",
                    style = LocalTypography.current.primary.regular,
                    color = LocalPalette.current.onSurface
                )

                DigitalTimer(
                    modifier = Modifier
                        .height(48.dp)
                        .padding(8.dp)
                        .fillMaxWidth(),
                    timerUiState = timerUiState
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f, false)
            ) {
                TimerToggleButton(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(start = 4.dp),
                    timerUiState = timerUiState,
                    playContent = { modifier ->
                        Icon(
                            modifier = modifier,
                            painter = painterResource(R.drawable.ic_control_play),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    },
                    pauseContent = { modifier ->
                        Icon(
                            modifier = modifier
                                .size(48.dp),
                            painter = painterResource(R.drawable.ic_control_pause),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    },
                    onClick = {
                        operationConfigUiActions.onToggleTimer()
                    }
                )
            }
        }
    }
}

@Composable
fun ToolbarFootsteps(
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .preferredFrameRate(FrameRateCategory.Normal)
        ) {

            var rememberSmoothedBPM by remember {
                mutableFloatStateOf(0f)
            }

            var rememberInstantBPM by remember {
                mutableFloatStateOf(0f)
            }

            var rememberSampledBPM by remember {
                mutableFloatStateOf(0f)
            }

            FootstepVisualizer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                graphBackgroundColor = Color.Unspecified,
                graphXAxisColor = LocalPalette.current.onSurface,
                graphYAxisColor = Color.Unspecified,
                sampleBackgroundColor = LocalPalette.current.surfaceContainer.copy(alpha = .5f),
                labelColor = LocalPalette.current.onSurface,
                endpointColor = LocalPalette.current.primary,
                lineSegmentColor = LocalPalette.current.primary,
                meterBeatLineColor = LocalPalette.current.onSurface,
                meterColor = LocalPalette.current.onSurface,
                meterOnColor = LocalPalette.current.tertiary,
                alpha = .01f,
                viewportBPM = 360,
                viewportDuration = 10.seconds,
                durationSplit = 10f,
                bpmSplit = 120f,
                samplingInterval = 3.seconds
            ) { instantBPM, smoothedBPM, sampleAverage ->
                rememberSmoothedBPM = smoothedBPM
                rememberInstantBPM = instantBPM
                rememberSampledBPM = sampleAverage
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "BPM: ${rememberSmoothedBPM.toInt()}; IPM: ${rememberInstantBPM.toInt()}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "MSPS: ${rememberSmoothedBPM/60f}; MIPM: ${rememberInstantBPM/60f}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "AVG BPM: $rememberSampledBPM",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "AVG MPS: ${rememberSampledBPM/60f}",
                color = LocalPalette.current.onSurface
            )

        }
    }

}

@Composable
fun ToolbarOperationAnalysis(
    modifier: Modifier = Modifier,
    operationDetailsUiState: OperationDetailsUiState
) {
    Box (
        modifier = modifier
    ) {
        OperationDetails(
            modifier = modifier,
            operationDetailsUiState = operationDetailsUiState
        )
    }
}

@Composable
fun OperationToolbar(
    modifier: Modifier = Modifier,
    toolbarUiState: ToolbarUiState,
    toolbarUiActions: ToolbarUiActions
) {

    InvestigationToolbar(
        modifier = modifier
            .heightIn(min = 48.dp),
        stickyContentStart = {

            ToolbarItem(
                onClick = {}
            ){
                CollapseButton(
                    modifier = Modifier
                        .size(48.dp),
                    isCollapsed = toolbarUiState.isCollapsed,
                    icon = R.drawable.ic_arrow_chevron_right,
                    disabledRotationVertical = 90,
                    disabledRotationHorizontal = 90,
                    enabledRotationAddition = 180,
                    onClick = { toolbarUiActions.onToggleCollapseToolbar() }
                )
            }

        },
        stickyContentEnd = {

            ToolbarItem(
                onClick = {}
            ){
                ResetButton {
                    toolbarUiActions.onReset()
                }
            }

        }
    ) {

        ToolbarItem(
            onClick = {
                toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_CONFIG)
            }
        ){
            Image(
                modifier = modifier,
                imageVector = getGearVector(
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_CONFIG) {
                        IconVectorColors.defaults(
                            fillColor = Color.Transparent,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor =Color.Transparent,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }

        ToolbarItem(
            onClick = {
                toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_ANALYZER)
            }
        ){
            Image(
                modifier = modifier,
                imageVector = getInfoVector(
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_ANALYZER) {
                        IconVectorColors.defaults(
                            fillColor = Color.Transparent,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = Color.Transparent,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }

        ToolbarItem(
            onClick = {
                toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_FOOTSTEP)
            }
        ){
            Image(
                modifier = modifier,
                imageVector = getExitVector(
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_FOOTSTEP) {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.primary,
                            strokeColor = Color.Transparent,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = Color.Transparent,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    }
}