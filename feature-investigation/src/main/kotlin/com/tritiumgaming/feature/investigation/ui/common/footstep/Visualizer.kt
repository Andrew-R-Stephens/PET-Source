package com.tritiumgaming.feature.investigation.ui.common.footstep

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.datastructs.CircularQueueLinkedList
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun FootstepVisualizer(
    modifier: Modifier = Modifier,
    footstepVisualizerUiState: FootstepVisualizerUiState,
    graphUiState: GraphUiState,
    graphColors: GraphColors,
    graphLabelColors: GraphLabelColors,
    realtimePlotColors: RealtimePlotColors,
    verticalMeterColors: VerticalMeterColors,
    onUpdate: ((instantBPM: Float, smoothedBPM: Float, intervalSampleBPM: Float) -> Unit) = {_, _, _ -> }
) {
    var now = System.currentTimeMillis()

    var tapUiState by remember {
        mutableStateOf(TapUiState(
            instantBPM = 0f,
            smoothedBPM = 0f,
            potentialBPM = 0f,
            lastTapTime = now,
            taps = CircularQueueLinkedList(50)
        ))
    }

    //var taps by remember { mutableStateOf(CircularQueueLinkedList<TapRecord>(50)) }

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

    // Real-time decay if no taps occur
    LaunchedEffect(ticker) {
        if (tapUiState.lastTapTime != 0L) {
            val delta = now - tapUiState.lastTapTime

            val potentialBPM = (60000f / delta)

            var smoothedBPM = (footstepVisualizerUiState.alpha * tapUiState.instantBPM) + ((1f - footstepVisualizerUiState.alpha) * tapUiState.instantBPM)
            if(smoothedBPM > potentialBPM) {
                smoothedBPM = potentialBPM
            }
            if (smoothedBPM < 1f) smoothedBPM = 0f

            tapUiState = tapUiState.copy(
                smoothedBPM = smoothedBPM,
                potentialBPM = potentialBPM
            )
        }

        if(tapUiState.lastTapTime + footstepVisualizerUiState.viewportDuration.inWholeMilliseconds < now) {
            tapUiState.taps.clear()
        }

    }

    val calculateSampleIntervalBPM: () -> Pair<Float, Float> = {

        val targetTime = now - footstepVisualizerUiState.samplingInterval.inWholeMilliseconds

        var intervalSum = 0f
        var intervalAverageSum = 0f
        var intervalCount = 0f

        var currentTap = tapUiState.taps.head
        while(currentTap != null) {
            if(currentTap.data.time > targetTime) {
                intervalSum += currentTap.data.bpm
                intervalAverageSum += currentTap.data.intervalAverage

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
        if (tapUiState.lastTapTime != 0L) {
            val delta = now - tapUiState.lastTapTime

            val instantBPM = (60000f / delta)

            var smoothedBPM = if (tapUiState.smoothedBPM == 0f) { instantBPM }
            else { (footstepVisualizerUiState.alpha * instantBPM) + ((1f - footstepVisualizerUiState.alpha) * instantBPM) }

            if (smoothedBPM < 1f) smoothedBPM = 0f

            tapUiState = tapUiState.copy(
                instantBPM = instantBPM,
                smoothedBPM = smoothedBPM
            )

            val intervalBPM = calculateSampleIntervalBPM()
            val intervalAverage = intervalBPM.first
            val intervalWeightedAverage = intervalBPM.second

            tapUiState.taps.enqueue(
                TapRecord(
                    time = now,
                    bpm = instantBPM,
                    intervalAverage = intervalAverage,
                    intervalWeightedAverage = intervalWeightedAverage
                ))
        }

        tapUiState = tapUiState.copy(
            lastTapTime = now
        )

        onUpdate(
            tapUiState.instantBPM,
            tapUiState.smoothedBPM,
            calculateSampleIntervalBPM().first
        )
    }

    val bpmRatioSmooth = (tapUiState.smoothedBPM / footstepVisualizerUiState.viewportBPM).coerceIn(0f, 1f)
    val bpmRatioPredictive = (tapUiState.potentialBPM / footstepVisualizerUiState.viewportBPM).coerceIn(0f, 1f)

    val realtimePlotUiState = RealtimePlotUiState(
        currentTime = now,
        bpmRatio = bpmRatioSmooth,
        viewportDuration = footstepVisualizerUiState.viewportDuration,
        viewportBPM = footstepVisualizerUiState.viewportBPM,
        taps = tapUiState.taps.asList()
    )

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

            VerticalScale(
                modifier = Modifier
                    .width(24.dp)
                    .fillMaxHeight(),
                graphLabelColors = graphLabelColors,
                viewportBpm = footstepVisualizerUiState.viewportBPM,
                bpmSplit = footstepVisualizerUiState.bpmSplit
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {

                Graph(
                    modifier = Modifier
                        .fillMaxSize(),
                    graphColors = graphColors,
                    graphUiState = graphUiState
                )

                BeatLine(
                    modifier = Modifier
                        .fillMaxSize(),
                    yPosition = bpmRatioSmooth,
                    color = Color.White
                )

                RealtimePlot(
                    modifier = Modifier
                        .fillMaxSize(),
                    realtimePlotColors = realtimePlotColors,
                    realtimePlotUiState = realtimePlotUiState
                )

            }

            Box(
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight(),
            ) {

                VerticalMeter(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = verticalMeterColors.meterColor,
                    onColor = verticalMeterColors.meterOnColor,
                    smoothRatio = bpmRatioSmooth,
                    predictiveRatio = bpmRatioPredictive
                )

            }
        }

        HorizontalScale(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .padding(start = 24.dp, end = 12.dp),
            graphLabelColors = graphLabelColors,
            viewportDuration = footstepVisualizerUiState.viewportDuration.inWholeMilliseconds,
            durationSpit = footstepVisualizerUiState.durationSplit
        )

    }
}

@Composable
private fun VerticalMeter(
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    onColor: Color = Color.Unspecified,
    smoothRatio: Float = 0f,
    predictiveRatio: Float = 0f
) {
    Canvas(
        modifier = modifier
    ) {
        val predictiveRatioY = size.height * (1f - predictiveRatio)
        drawLine(
            color = onColor,
            start = Offset(0f, predictiveRatioY),
            end = Offset(size.width, predictiveRatioY)
        )

        drawRect(
            style = Fill,
            color = color,
            size = Size(size.width, size.height * smoothRatio),
            topLeft = Offset(0f, size.height * (1f - smoothRatio)),
        )
    }
}


@Composable
private fun VerticalScale(
    modifier: Modifier,
    graphLabelColors: GraphLabelColors,
    viewportBpm: Int,
    bpmSplit: Float
) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(color = graphLabelColors.label, fontSize = 10.sp)

    val quantizedMax = ceil(viewportBpm / bpmSplit) * bpmSplit
    val steps = (quantizedMax / bpmSplit).toInt()

    Canvas(modifier) {
        clipRect(
            size.width * -.25f,
            size.height * -.25f,
            size.width * 1.5f,
            size.height * 1.5f
        ) {
            for (step in 0..steps) {
                val bpm = step * bpmSplit
                val yRatio = bpm / quantizedMax
                val y = size.height - (size.height * yRatio)

                val mps = bpm / 60f

                val label = "${ mps.toInt() } m"

                drawLine(
                    color = graphLabelColors.labelLine,
                    start = Offset(size.width, y),
                    end = Offset(size.width - 24, y)
                )

                drawText(
                    textMeasurer = textMeasurer,
                    text = label,
                    style = textStyle,
                    topLeft = Offset(
                        size.width - textMeasurer.measure(
                            text = label,
                            style = textStyle,
                            maxLines = 1
                        ).size.width.toFloat(),
                        y - (textMeasurer.measure(
                            text = label,
                            style = textStyle,
                            maxLines = 1
                        ).size.height.toFloat())
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
            }
        }

    }
}

@Composable
private fun HorizontalScale(
    modifier: Modifier,
    graphLabelColors: GraphLabelColors,
    viewportDuration: Long,
    durationSpit: Float
) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(color = graphLabelColors.label, fontSize = 10.sp)

    Canvas(modifier) {
        clipRect(
            size.width * -.25f,
            size.height * -.25f,
            size.width * 1.5f,
            size.height * 1.5f
        ) {
            val seconds = (viewportDuration / 1.seconds.inWholeMilliseconds).toInt()
            val labelCount = seconds / durationSpit

            for (i in 0..durationSpit.toInt()) {

                val time = labelCount * i

                val label = "${time.toInt()}s"
                val x = size.width - (size.width * (time / seconds))

                drawText(
                    textMeasurer = textMeasurer,
                    text = label,
                    style = textStyle,
                    topLeft = Offset(
                        x - textMeasurer.measure(
                            text = label,
                            style = textStyle,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Visible
                        ).size.width.toFloat() * .5f,
                        y = 0f
                    ),
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}

@Composable
private fun Graph(
    modifier: Modifier = Modifier,
    graphColors: GraphColors,
    graphUiState: GraphUiState
) {
    val xInterval = graphUiState.xInterval
    val yInterval = graphUiState.yInterval
    val viewportDuration: Duration = graphUiState.viewportDuration
    val samplingInterval: Duration = graphUiState.samplingInterval

    Canvas(
        modifier = modifier
            .background(graphColors.surface)
    ) {
        clipRect(
            0f, 0f, size.width, size.height
        ) {
            if(graphColors.xAxis != Color.Unspecified && xInterval > 0f) {
                val axisRatio: Float = size.width / xInterval
                for (i in 0 until xInterval.toInt() + 1) {
                    val x = axisRatio * i
                    drawLine(
                        color = graphColors.xAxis,
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                    )

                }
            }

            if(graphColors.yAxis != Color.Unspecified && yInterval > 0f) {
                val axisRatio: Float = size.height / yInterval
                for (i in 0 until yInterval.toInt() + 1) {
                    val y = axisRatio * i
                    drawLine(
                        color = graphColors.yAxis,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                    )

                }
            }

            val samplingIntervalRatio =
                samplingInterval.inWholeMilliseconds / viewportDuration.inWholeMilliseconds.toFloat()
            drawRect(
                color = graphColors.surfaceContainer,
                topLeft = Offset(
                    x = size.width - (size.width * samplingIntervalRatio),
                    y = 0f
                ),
                size = Size(
                    width = size.width,
                    height = size.height
                )
            )


        }
    }
}

@Composable
private fun RealtimePlot(
    modifier: Modifier = Modifier,
    realtimePlotColors: RealtimePlotColors,
    realtimePlotUiState: RealtimePlotUiState
) {

    val currentTime = realtimePlotUiState.currentTime

    val viewportBPM = realtimePlotUiState.viewportBPM
    val viewportDuration = realtimePlotUiState.viewportDuration

    val bpmRatio = realtimePlotUiState.bpmRatio

    val taps = realtimePlotUiState.taps

    Canvas(
        modifier = modifier
    ) {

        clipRect(
            0f, 0f, size.width, size.height
        ) {

            drawLine(
                color = realtimePlotColors.meterBeatLine,
                start = Offset(0f, size.height * (1f - bpmRatio)),
                end = Offset(size.width, size.height * (1f - bpmRatio)),
            )

            taps ?: return@Canvas

            val xPlotRatio: Float = size.width / viewportDuration.inWholeMilliseconds

            if (taps.isNotEmpty()) {
                val instantPath = Path()
                val averagePath = Path()
                val weightedAveragePath = Path()

                val firstTap = taps.first()

                val startX = size.width - ((currentTime - firstTap.time) * xPlotRatio)
                val startY0 = size.height * (1f - (firstTap.bpm / viewportBPM))
                val startY1 = size.height * (1f - (firstTap.intervalAverage / viewportBPM))
                val startY2 = size.height * (1f - (firstTap.intervalWeightedAverage / viewportBPM))

                instantPath.moveTo(startX, startY0)
                averagePath.moveTo(startX, startY1)
                weightedAveragePath.moveTo(startX, startY2)

                for (i in 1 until taps.size) {
                    val prevTap = taps[i - 1]
                    val currTap = taps[i]

                    val x1 = size.width - ((currentTime - prevTap.time) * xPlotRatio)
                    val y10 = size.height * (1f - (prevTap.bpm / viewportBPM))
                    val y11 = size.height * (1f - (prevTap.intervalAverage / viewportBPM))
                    val y12 = size.height * (1f - (prevTap.intervalWeightedAverage / viewportBPM))
                    val x2 = size.width - ((currentTime - currTap.time) * xPlotRatio)
                    val y20 = size.height * (1f - (currTap.bpm / viewportBPM))
                    val y21 = size.height * (1f - (currTap.intervalAverage / viewportBPM))
                    val y22 = size.height * (1f - (currTap.intervalWeightedAverage / viewportBPM))

                    val midX = (x1 + x2) / 2f
                    val midY1 = (y11 + y21) / 2f
                    val midY2 = (y12 + y22) / 2f

                    instantPath.lineTo(
                        x1,
                        y10
                    )

                    averagePath.quadraticTo(
                        x1,
                        y11,
                        midX,
                        midY1)

                    weightedAveragePath.quadraticTo(
                        x1,
                        y12,
                        midX,
                        midY2)

                    if (i == taps.size - 1) {
                        instantPath.lineTo(x2, y20)
                        averagePath.lineTo(x2, y21)
                        weightedAveragePath.lineTo(x2, y22)
                    }
                }

                drawPath(
                    path = instantPath,
                    style = Stroke(width = 2f),
                    color = realtimePlotColors.instant
                )

                drawPath(
                    path = weightedAveragePath,
                    style = Stroke(width = 2f),
                    color = realtimePlotColors.weighted
                )

                drawPath(
                    path = averagePath,
                    style = Stroke(width = 2f),
                    color = realtimePlotColors.smoothed
                )
            }

            /*val linePath = Path()
            taps.forEachIndexed { index, tap ->
                val x = size.width - ((currentTime - tap.time) * xPlotRatio)
                val y = size.height * (1f - (tap.bpm / viewportBPM))
                if(index > 0) {
                    val prevTap = taps[index - 1]
                        drawLine(
                            strokeWidth = 1f,
                            color = Color.Red,
                            start = Offset(
                                x = size.width - ((currentTime - prevTap.time) * xPlotRatio),
                                y = size.height * (1f - (prevTap.bpm / viewportBPM))
                            ),
                            end = Offset(
                                x = size.width - ((currentTime - tap.time) * xPlotRatio),
                                y = size.height * (1f - (tap.bpm / viewportBPM))
                            )
                        )
                        linePath.lineTo(x = x, y = y)
                    } else {
                        linePath.moveTo(x = x, y = y)
                    }
                }

                drawPath(
                    path = linePath,
                    alpha = 1f,
                    style = Stroke(width = 1f),
                    color = lineSegmentColors.instant
                )*/

            /*taps.forEachIndexed { index, tap ->
                if(index > 0) {
                    val prevTap = taps[index - 1]
                    drawLine(
                        strokeWidth = 1f,
                        color = Color.Red,
                        start = Offset(
                            x = size.width - ((currentTime - prevTap.time) * xPlotRatio),
                            y = size.height * (1f - (prevTap.bpm / viewportBPM.toFloat()))
                        ),
                        end = Offset(
                            x = size.width - ((currentTime - tap.time) * xPlotRatio),
                            y = size.height * (1f - (tap.bpm / viewportBPM.toFloat()))
                        )
                    )
                }
            }*/

            taps.forEach { tap ->
                drawCircle(
                    color = realtimePlotColors.instant,
                    radius = 4f,
                    center = Offset(
                        x = size.width - ((currentTime - tap.time) * xPlotRatio),
                        y = size.height * (1f - (tap.bpm / viewportBPM.toFloat()))
                    )
                )
            }
        }
    }
}

@Composable
private fun BeatLine(
    modifier: Modifier = Modifier,
    yPosition: Float,
    color: Color,
) {

    Canvas(
        modifier = modifier
    ) {

        clipRect(
            0f, 0f, size.width, size.height
        ) {

            drawLine(
                color = color,
                start = Offset(0f, size.height * (1f - yPosition)),
                end = Offset(size.width, size.height * (1f - yPosition)),
            )

        }
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
                modifier = Modifier
            ) {

                Column (
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
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
    }
}
