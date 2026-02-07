package com.tritiumgaming.feature.investigation.ui.common.footstep

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.common.util.datastructs.CircularQueueLinkedList
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.ui.common.footstep.beatline.BeatLine
import com.tritiumgaming.feature.investigation.ui.common.footstep.beatline.BeatLineUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.beatline.BeatLineUiState
import com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer.VisualizerUiActions
import com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer.PointRecord
import com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer.TapUiState
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphlabels.GraphLabelsUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphlabels.GraphLabelsXAxis
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphlabels.GraphLabelsYAxis
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphsurface.GraphSurface
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphsurface.GraphSurfaceUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeplot.RealtimePlot
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeplot.RealtimePlotUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeplot.RealtimePlotUiState
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeverticalmeter.RealtimeVerticalMeter
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeverticalmeter.RealtimeVerticalMeterColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeverticalmeter.RealtimeVerticalMeterUiState
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun FootstepVisualizer(
    modifier: Modifier = Modifier,
    stateBundle: FootstepVisualizerUiStateBundle,
    colorBundle: BpmVisualizerUiColorBundle,
    actions: VisualizerUiActions
) {
    val state = stateBundle.visualizerUiState

    var now = System.currentTimeMillis()

    var tapUiState by remember {
        mutableStateOf(
            TapUiState(
                instant = 0f,
                smoothed = 0f,
                potential = 0f,
                recordedTime = now,
                points = CircularQueueLinkedList(50)
            )
        )
    }

    val infiniteTransition = rememberInfiniteTransition(label = "BPMDecay")
    val ticker by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                1000,
                easing = LinearEasing)
        ),
        label = "Ticker"
    )

    val bpmRatioSmooth = (tapUiState.smoothed / state.range).coerceIn(0f, 1f)
    val bpmRatioPredictive = (tapUiState.potential / state.range).coerceIn(0f, 1f)

    val realtimePlotUiState = RealtimePlotUiState(
        currentTime = now,
        yRelative = bpmRatioSmooth,
        viewportXInterval = state.domain,
        viewportYInterval = state.range.toFloat(),
        taps = tapUiState.points.asList()
    )

    val beatLineUiState = BeatLineUiState(
        smoothedBPM = bpmRatioSmooth
    )

    // Real-time decay if no taps occur
    LaunchedEffect(ticker) {
        if (tapUiState.recordedTime != 0L) {
            val delta = now - tapUiState.recordedTime

            val potentialBPM = (60000f / delta)

            var smoothedBPM = (state.alpha * tapUiState.instant) +
                    ((1f - state.alpha) * tapUiState.instant)
            if(smoothedBPM > potentialBPM) {
                smoothedBPM = potentialBPM
            }
            if (smoothedBPM < 1f) smoothedBPM = 0f

            tapUiState = tapUiState.copy(
                smoothed = smoothedBPM,
                potential = potentialBPM
            )
        }

        if(tapUiState.recordedTime + state.domain < now) {
            tapUiState.points.clear()
        }

    }

    val calculateSampleIntervalBPM: () -> Pair<Float, Float> = {

        val targetTime = now - state.subDomain

        var intervalSum = 0f
        var intervalAverageSum = 0f
        var intervalCount = 0f

        var currentTap = tapUiState.points.head
        while(currentTap != null) {
            if(currentTap.data.pX > targetTime) {
                intervalSum += currentTap.data.pY
                intervalAverageSum += currentTap.data.avg

                intervalCount++
            }

            currentTap = currentTap.next
        }

        val intervalAverage = intervalSum/(intervalCount.coerceAtLeast(1f))

        Pair(
            intervalAverage,
            intervalAverageSum/(intervalCount.coerceAtLeast(1f))
        )
    }

    val onBeat = {
        if (tapUiState.recordedTime != 0L) {
            val delta = now - tapUiState.recordedTime

            val instantBPM = (60000f / delta)

            var smoothedBPM = if (tapUiState.smoothed == 0f) { instantBPM }
            else { (state.alpha * instantBPM) + ((1f - state.alpha) * instantBPM) }

            if (smoothedBPM < 1f) smoothedBPM = 0f

            tapUiState = tapUiState.copy(
                instant = instantBPM,
                smoothed = smoothedBPM
            )

            val intervalBPM = calculateSampleIntervalBPM()

            tapUiState.points.enqueue(
                PointRecord(
                    pX = now,
                    pY = instantBPM,
                    avg = intervalBPM.first,
                    weightedAvg = intervalBPM.second
                )
            )
        }

        tapUiState = tapUiState.copy(
            recordedTime = now
        )

        actions.onUpdate(
            tapUiState
        )
    }

    Column(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        down.consume()

                        now = System.currentTimeMillis()
                        onBeat()

                        waitForUpOrCancellation()
                    }
                }
            }
    ) {

        Row(
            modifier = Modifier
                .weight(1f)
        ) {

            GraphLabelsYAxis(
                modifier = Modifier
                    .width(24.dp)
                    .fillMaxHeight(),
                colors = colorBundle.graphLabelsYAxisUiColors,
                state = stateBundle.graphLabelsYUiState,
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {

                GraphSurface(
                    modifier = Modifier
                        .fillMaxSize(),
                    graphSurfaceUiColors = colorBundle.graphSurfaceUiColors,
                    graphSurfaceUiState = stateBundle.graphSurfaceUiState
                )

                BeatLine(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = beatLineUiState,
                    colors = colorBundle.beatLineUiColors
                )

                RealtimePlot(
                    modifier = Modifier
                        .fillMaxSize(),
                    realtimePlotUiColors = colorBundle.realtimePlotUiColors,
                    realtimePlotUiState = realtimePlotUiState
                )

            }

            Box(
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight(),
            ) {

                RealtimeVerticalMeter(
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = colorBundle.realtimeVerticalMeterColors,
                    state = RealtimeVerticalMeterUiState(
                        current = bpmRatioSmooth,
                        predictive = bpmRatioPredictive
                    )
                )

            }
        }

        GraphLabelsXAxis(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .padding(start = 24.dp, end = 12.dp),
            colors = colorBundle.graphLabelsXAxisUiColors,
            state = stateBundle.graphLabelsXUiState
        )

    }
}

@Composable
@Preview
private fun Preview() {

    SelectiveTheme {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            val footstepVisualizerUiStateBundle = FootstepVisualizerUiStateBundle(
                alpha = .01f,
                range = 360,
                domain = 10.seconds.inWholeMilliseconds,
                domainInterval = 10f,
                rangeInterval = 120f,
                subDomain = 3.seconds.inWholeMilliseconds
            )

            val bpmVisualizerUiColorBundle = BpmVisualizerUiColorBundle(
                realtimePlotUiColors = RealtimePlotUiColors(
                    instant = LocalPalette.current.primary,
                    smoothed = LocalPalette.current.tertiary,
                    weighted = LocalPalette.current.onTertiary,
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
                    surface = Color.Unspecified,
                    surfaceContainer = LocalPalette.current.surfaceContainer.copy(alpha = 1f),
                    xAxis = LocalPalette.current.onSurface,
                    yAxis = Color.Unspecified
                )
            )

            Box (
                modifier = Modifier
            ) {

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
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
                        text = "BPM: ${String.format("%.2f", tapUiState.smoothed) }; " +
                                "IPM: ${String.format("%.2f", tapUiState.instant) }",
                        color = LocalPalette.current.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        text = "MSPS: ${String.format("%.2f", tapUiState.smoothed/60f) }; " +
                                "MIPM: ${String.format("%.2f", tapUiState.instant/60f)}",
                        color = LocalPalette.current.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        text = "AVG BPM: ${
                            String.format("%.2f", tapUiState.points.tail?.data?.avg ?: 0f)}",
                        color = LocalPalette.current.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        text = "AVG MPS: ${tapUiState.points.tail?.data?.avg
                            ?.let { String.format("%.2f", it/60f)}}",
                        color = LocalPalette.current.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        text = "WAVG MPS: ${tapUiState.points.tail?.data?.weightedAvg
                            ?.let { String.format("%.2f", it/60f)}}",
                        color = LocalPalette.current.onSurface
                    )

                }
            }

        }
    }
}
