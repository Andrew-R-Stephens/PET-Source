package com.tritiumgaming.feature.investigation.ui.section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.ui.common.footstep.FootstepVisualizer
import com.tritiumgaming.feature.investigation.ui.common.footstep.FootstepVisualizerUiState
import com.tritiumgaming.feature.investigation.ui.common.footstep.GraphColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.GraphLabelColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.GraphUiState
import com.tritiumgaming.feature.investigation.ui.common.footstep.RealtimePlotColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.VerticalMeterColors
import kotlin.Float
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


@Composable
fun ToolbarFootstepsVisualizerSection(
    modifier: Modifier = Modifier
) {

    val footstepVisualizerUiState = FootstepVisualizerUiState(
        alpha = .01f,
        viewportBPM = 360,
        viewportDuration = 10.seconds,
        durationSplit = 10f,
        bpmSplit = 120f,
        samplingInterval = 3.seconds
    )

    val quantizedMax = ceil(
        footstepVisualizerUiState.viewportBPM / footstepVisualizerUiState.bpmSplit) *
            footstepVisualizerUiState.bpmSplit
    val steps = (quantizedMax / footstepVisualizerUiState.bpmSplit).toInt()

    val realtimePlotColors = RealtimePlotColors(
        instant = LocalPalette.current.primary,
        smoothed = LocalPalette.current.tertiary,
        weighted = LocalPalette.current.secondary,
        meterBeatLine = LocalPalette.current.onSurface
    )

    val verticalMeterColors = VerticalMeterColors(
        meterColor = LocalPalette.current.onSurface,
        meterOnColor = LocalPalette.current.tertiary,
    )

    val graphUiState = GraphUiState(
        xInterval = footstepVisualizerUiState.durationSplit,
        yInterval = steps.toFloat(),
        viewportDuration = footstepVisualizerUiState.viewportDuration,
        samplingInterval = footstepVisualizerUiState.samplingInterval
    )

    val graphColors = GraphColors(
        surface = Color.Unspecified,
        surfaceContainer = LocalPalette.current.surfaceContainer.copy(alpha = .5f),
        xAxis = LocalPalette.current.onSurface,
        yAxis = Color.Unspecified
    )

    val graphLabelColors = GraphLabelColors(
        label = LocalPalette.current.onSurface
    )

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
                footstepVisualizerUiState = footstepVisualizerUiState,
                graphColors = graphColors,
                realtimePlotColors = realtimePlotColors,
                verticalMeterColors = verticalMeterColors,
                graphUiState = graphUiState,
                graphLabelColors = graphLabelColors,
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
