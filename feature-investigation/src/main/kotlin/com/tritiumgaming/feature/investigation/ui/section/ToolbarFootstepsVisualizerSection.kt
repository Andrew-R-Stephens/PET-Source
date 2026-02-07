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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.ui.common.footstep.beatline.BeatLineUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.FootstepVisualizer
import com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer.VisualizerUiActions
import com.tritiumgaming.feature.investigation.ui.common.footstep.BpmVisualizerUiColorBundle
import com.tritiumgaming.feature.investigation.ui.common.footstep.FootstepVisualizerUiStateBundle
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphlabels.GraphLabelsUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphsurface.GraphSurfaceUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeplot.RealtimePlotUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeverticalmeter.RealtimeVerticalMeterColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer.TapUiState
import kotlin.time.Duration.Companion.seconds

@Composable
fun ToolbarFootstepsVisualizerSection(
    modifier: Modifier = Modifier
) {
    val footstepVisualizerUiStateBundle = FootstepVisualizerUiStateBundle(
        alpha = .5f,
        viewportY = 360,
        viewportX = 10.seconds.inWholeMilliseconds,
        intervalX = 10f,
        intervalY = 120f,
        samplingXInterval = 3.seconds.inWholeMilliseconds
    )

    /*val footstepVisualizerUiStateBundle = FootstepVisualizerUiStateBundle(
        alpha = 1f,
        viewportY = 360,
        viewportX = 10.seconds.inWholeMilliseconds,
        intervalX = 10f,
        intervalY = 120f,
        samplingXInterval = 3.seconds.inWholeMilliseconds
    )*/

    val bpmVisualizerUiColorBundle = BpmVisualizerUiColorBundle(
        realtimePlotUiColors = RealtimePlotUiColors(
            instant = LocalPalette.current.primary,
            smoothed = LocalPalette.current.tertiary,
            weighted = LocalPalette.current.secondary,
            line = LocalPalette.current.onSurface
        ),
        realtimeVerticalMeterColors = RealtimeVerticalMeterColors(
            color = LocalPalette.current.onSurface,
            onColor = LocalPalette.current.tertiary,
        ),
        beatLineUiColors = BeatLineUiColors(
            color = LocalPalette.current.onSurface
        ),
        graphLabelsXAxisUiColors = GraphLabelsUiColors(
            label = LocalPalette.current.onSurface,
        ),
        graphLabelsYAxisUiColors = GraphLabelsUiColors(
            label = LocalPalette.current.onSurface,
        ),
        graphSurfaceUiColors = GraphSurfaceUiColors(
            surface = LocalPalette.current.surface,
            surfaceContainer = LocalPalette.current.surfaceContainer,
            xAxis = LocalPalette.current.onSurface,
            yAxis = Color.Unspecified
        )
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

            var tapUiState by remember{
                mutableStateOf(TapUiState())
            }

            FootstepVisualizer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                stateBundle = footstepVisualizerUiStateBundle,
                colorBundle = bpmVisualizerUiColorBundle,
                actions = VisualizerUiActions(
                    onUpdate = { newTapUiState ->
                        tapUiState = newTapUiState
                    }
                )
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "BPM: ${tapUiState.smoothed.toInt()}; IPM: ${tapUiState.instant.toInt()}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "MSPS: ${tapUiState.smoothed/60f}; MIPM: ${tapUiState.instant/60f}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "AVG BPM: ${tapUiState.points.tail?.data?.avg ?: 0f}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "AVG MPS: ${tapUiState.points.tail?.data?.avg
                    ?.let { avg -> avg / 60f }}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "WAVG MPS: ${tapUiState.points.tail?.data?.weightedAvg
                    ?.let { wavg -> wavg / 60f }}",
                color = LocalPalette.current.onSurface
            )

        }
    }

}
