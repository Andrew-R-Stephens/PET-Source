package com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer

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
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.beatline.BeatLine
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.beatline.BeatLineUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.beatline.BeatLineUiState
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels.GraphLabelsUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels.GraphLabelsXAxis
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels.GraphLabelsYAxis
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphsurface.GraphSurface
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphsurface.GraphSurfaceUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeplot.RealtimePlot
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeplot.RealtimePlotUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeplot.RealtimePlotUiState
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeverticalmeter.RealtimeVerticalMeter
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeverticalmeter.RealtimeVerticalMeterColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeverticalmeter.RealtimeVerticalMeterUiState
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.PointRecord
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun BpmVisualizer(
    modifier: Modifier = Modifier,
    stateBundle: BpmVisualizerStateBundle,
    colorBundle: BpmVisualizerColorBundle,
    actions: BpmVisualizerUiActions<RealtimeUiState<PointRecord>>
) {
    val state = stateBundle.visualizerUiState

    var now = System.currentTimeMillis()

    var realtimeUiState by remember {
        mutableStateOf(
            RealtimeUiState<PointRecord>(
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

    val bpmRatioSmooth = (realtimeUiState.smoothed / state.range).coerceIn(0f, 1f)
    val bpmRatioPredictive = (realtimeUiState.potential / state.range).coerceIn(0f, 1f)

    val realtimePlotUiState = RealtimePlotUiState(
        currentTime = now,
        yRelative = bpmRatioSmooth,
        viewportXInterval = state.domain,
        viewportYInterval = state.range.toFloat(),
        taps = realtimeUiState.points.asList()
    )

    val beatLineUiState = BeatLineUiState(
        smoothedBPM = bpmRatioSmooth
    )

    // Real-time decay if no taps occur
    LaunchedEffect(ticker) {
        if (realtimeUiState.recordedTime != 0L) {
            val delta = now - realtimeUiState.recordedTime

            val potentialBPM = (60000f / delta)

            var smoothedBPM = (state.alpha * realtimeUiState.instant) +
                    ((1f - state.alpha) * realtimeUiState.instant)
            if(smoothedBPM > potentialBPM) {
                smoothedBPM = potentialBPM
            }
            if (smoothedBPM < 1f) smoothedBPM = 0f

            realtimeUiState = realtimeUiState.copy(
                smoothed = smoothedBPM,
                potential = potentialBPM
            )
        }

        if(realtimeUiState.recordedTime + state.domain < now) {
            realtimeUiState.points.clear()
        }

    }

    val calculateSampleIntervalBPM: () -> Pair<Float, Float> = {

        val targetTime = now - state.domainSampleInterval

        var intervalSum = 0f
        var intervalAverageSum = 0f
        var intervalCount = 0f

        var currentTap = realtimeUiState.points.head
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
        var intervalBpm: Pair<Float, Float>? = null

        if (realtimeUiState.recordedTime != 0L) {
            val delta = now - realtimeUiState.recordedTime

            val instantBPM = (60000f / delta)

            var smoothedBPM = if (realtimeUiState.smoothed == 0f) { instantBPM }
            else { (state.alpha * instantBPM) + ((1f - state.alpha) * instantBPM) }

            if (smoothedBPM < 1f) smoothedBPM = 0f

            realtimeUiState = realtimeUiState.copy(
                instant = instantBPM,
                smoothed = smoothedBPM
            )

            intervalBpm = calculateSampleIntervalBPM()

            realtimeUiState.points.enqueue(
                PointRecord(
                    pX = now,
                    pY = instantBPM,
                    avg = intervalBpm.first,
                    weightedAvg = intervalBpm.second
                )
            )
        }

        realtimeUiState = realtimeUiState.copy(
            recordedTime = now,
            average = intervalBpm?.first ?: realtimeUiState.average,
            weightedAverage = intervalBpm?.second ?: realtimeUiState.weightedAverage
        )

        actions.onUpdate(
            realtimeUiState
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
                    state = stateBundle.graphSurfaceUiState,
                    colors = colorBundle.graphSurfaceUiColors,
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
                    colors = colorBundle.realtimePlotUiColors,
                    state = realtimePlotUiState
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

            val bpmVisualizerStateBundle = BpmVisualizerStateBundle(
                alpha = .01f,
                range = 360,
                domain = 10.seconds.inWholeMilliseconds,
                domainInterval = 10f,
                rangeInterval = 120f,
                domainSampleInterval = 3.seconds.inWholeMilliseconds
            )

            val bpmVisualizerColorBundle = BpmVisualizerColorBundle(
                realtimePlotUiColors = RealtimePlotUiColors(
                    instant = LocalPalette.current.primary,
                    averaged = LocalPalette.current.tertiary,
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
                    surface = LocalPalette.current.surfaceContainer,
                    surfaceContainer = LocalPalette.current.surfaceContainerHigh.copy(alpha = 1f),
                    domain = LocalPalette.current.onSurface.copy(alpha = .25f),
                    range = Color.Unspecified
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

                    var realtimeUiState by remember{
                        mutableStateOf(RealtimeUiState<PointRecord>())
                    }

                    BpmVisualizer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        stateBundle = bpmVisualizerStateBundle,
                        colorBundle = bpmVisualizerColorBundle,
                        actions = BpmVisualizerUiActions(
                            onUpdate = { newTapUiState ->
                                realtimeUiState = newTapUiState
                            }
                        )
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        text = "BPM: ${String.format("%.2f", realtimeUiState.smoothed) }; " +
                                "IPM: ${String.format("%.2f", realtimeUiState.instant) }",
                        color = LocalPalette.current.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        text = "MSPS: ${String.format("%.2f", realtimeUiState.smoothed/60f) }; " +
                                "MIPM: ${String.format("%.2f", realtimeUiState.instant/60f)}",
                        color = LocalPalette.current.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        text = "AVG BPM: ${
                            String.format("%.2f", realtimeUiState.points.tail?.data?.avg ?: 0f)}",
                        color = LocalPalette.current.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        text = "AVG MPS: ${realtimeUiState.points.tail?.data?.avg
                            ?.let { String.format("%.2f", it/60f)}}",
                        color = LocalPalette.current.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        text = "WAVG MPS: ${realtimeUiState.points.tail?.data?.weightedAvg
                            ?.let { String.format("%.2f", it/60f)}}",
                        color = LocalPalette.current.onSurface
                    )

                }
            }

        }
    }
}
