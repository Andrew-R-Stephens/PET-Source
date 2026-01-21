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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
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
@Preview
private fun Preview() {
    SelectiveTheme {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
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
                    .height(400.dp),
                graphBackgroundColor = Color.Unspecified,
                graphXAxisColor = Color.Gray,
                graphYAxisColor = Color.Unspecified,
                sampleBackgroundColor = Color.Blue.copy(alpha = .5f),
                labelColor = Color.Gray,
                endpointColor = Color.Red,
                lineSegmentColor = Color.Red,
                meterBeatLineColor = Color.White,
                meterColor = Color.Green,
                meterOnColor = Color.White,
                viewportBPM = 250,
                viewportDuration = 20.seconds,
                samplingInterval = 3.seconds,
                durationSplit = 10f,
                bpmSplit = 120f,
                alpha = .01f
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
fun FootstepVisualizer(
    modifier: Modifier = Modifier,
    graphBackgroundColor: Color = Color.Unspecified,
    graphXAxisColor: Color = Color.Unspecified,
    graphYAxisColor: Color = Color.Unspecified,
    sampleBackgroundColor: Color = Color.Unspecified,
    labelColor: Color = Color.Unspecified,
    endpointColor: Color = Color.Unspecified,
    lineSegmentColor: Color = Color.Unspecified,
    meterBeatLineColor: Color = Color.Unspecified,
    meterColor: Color = Color.Unspecified,
    meterOnColor: Color = Color.Unspecified,
    alpha: Float = .2f,
    viewportBPM: Int = 480,
    viewportDuration: Duration = 60.seconds,
    durationSplit: Float = 10f,
    bpmSplit: Float = 50f,
    samplingInterval: Duration = viewportDuration,
    onUpdate: ((instantBPM: Float, smoothedBPM: Float, intervalSampleBPM: Float) -> Unit) = {_, _, _ -> }
) {
    var now = System.currentTimeMillis()

    var instantBPM by remember { mutableFloatStateOf(0f) }
    var smoothedBPM by remember { mutableFloatStateOf(0f) }
    var potentialBPM by remember { mutableFloatStateOf(0f) }

    var lastTapTime by remember { mutableLongStateOf(now) }
    var taps by remember { mutableStateOf(CircularQueueLinkedList<TapRecord>(50)) }

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
        if (lastTapTime != 0L) {
            val delta = now - lastTapTime

            potentialBPM = (60000f / delta)

            smoothedBPM = (alpha * instantBPM) + ((1f - alpha) * instantBPM)

            if(smoothedBPM > potentialBPM) {
                smoothedBPM = potentialBPM
            }


            if (smoothedBPM < 1f) smoothedBPM = 0f
        }

        if(lastTapTime + viewportDuration.inWholeMilliseconds < now) {
            taps.clear()
        }

    }

    val calculateSampleIntervalBPM: () -> Float = {

        val targetTime = now - samplingInterval.inWholeMilliseconds

        var intervalSum = 0f
        var intervalCount = 0f

        var currentTap = taps.head
        while(currentTap != null) {
            if(currentTap.data.time > targetTime) {
                intervalSum += currentTap.data.bpm
                intervalCount++
            }
            currentTap = currentTap.next
        }

        intervalSum/(intervalCount.coerceAtLeast(1f))
    }

    val onBeat = {
        if (lastTapTime != 0L) {
            val delta = now - lastTapTime

            instantBPM = (60000f / delta)

            taps.enqueue(TapRecord(now, instantBPM))

            smoothedBPM = if (smoothedBPM == 0f) { instantBPM }
                else { (alpha * instantBPM) + ((1f - alpha) * instantBPM) }

            if (smoothedBPM < 1f) smoothedBPM = 0f
        }

        lastTapTime = now

        onUpdate(
            instantBPM,
            smoothedBPM,
            calculateSampleIntervalBPM()
        )
    }

    val bpmRatioSmooth = (smoothedBPM / viewportBPM).coerceIn(0f, 1f)
    val bpmRatioPredictive = (potentialBPM / viewportBPM).coerceIn(0f, 1f)

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
                labelColor = labelColor,
                axisLineColor = graphYAxisColor,
                viewportBpm = viewportBPM,
                bpmSplit = bpmSplit
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {

                val quantizedMax = ceil(viewportBPM / bpmSplit) * bpmSplit
                val steps = (quantizedMax / bpmSplit).toInt()

                Graph(
                    modifier = Modifier
                        .fillMaxSize(),
                    graphBackgroundColor = graphBackgroundColor,
                    sampleBackgroundColor = sampleBackgroundColor,
                    graphXAxisColor = graphXAxisColor,
                    graphYAxisColor = Color.Unspecified,
                    xAxisSplit = durationSplit,
                    yAxisSplit = steps.toFloat(),
                    viewportDuration = viewportDuration,
                    samplingInterval = samplingInterval
                )

                BeatLine(
                    modifier = Modifier,
                    bpmRatio = bpmRatioSmooth
                )

                RealtimePlot(
                    modifier = Modifier
                        .fillMaxSize(),
                    endpointColor = endpointColor,
                    lineSegmentColor = lineSegmentColor,
                    meterBeatLineColor = meterBeatLineColor,
                    currentTime = System.currentTimeMillis(),
                    bpmRatio = bpmRatioSmooth,
                    viewportDuration = viewportDuration,
                    viewportBPM = viewportBPM,
                    taps = taps.asList()
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
                    color = meterColor,
                    onColor = meterOnColor,
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
            labelColor = labelColor,
            viewportDuration = viewportDuration.inWholeMilliseconds,
            durationSpit = durationSplit
        )

    }
}

private class TapRecord(
    val time: Long,
    val bpm: Float
)

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
    labelColor: Color = Color.Unspecified,
    axisLineColor: Color = Color.Unspecified,
    viewportBpm: Int,
    bpmSplit: Float
) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(color = labelColor, fontSize = 10.sp)

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
                    color = axisLineColor,
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
    labelColor: Color = Color.Unspecified,
    viewportDuration: Long,
    durationSpit: Float
) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(color = labelColor, fontSize = 10.sp)

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
    graphBackgroundColor: Color = Color.Unspecified,
    sampleBackgroundColor: Color,
    graphXAxisColor: Color = Color.Unspecified,
    graphYAxisColor: Color = Color.Unspecified,
    xAxisSplit: Float = 10f,
    yAxisSplit: Float = 10f,
    viewportDuration: Duration = 60.seconds,
    samplingInterval: Duration = 30.seconds
) {

    Canvas(
        modifier = modifier
            .background(graphBackgroundColor)
    ) {
        clipRect(
            0f, 0f, size.width, size.height
        ) {
            if(graphXAxisColor != Color.Unspecified && xAxisSplit > 0f) {
                val axisRatio: Float = size.width / xAxisSplit
                for (i in 0 until xAxisSplit.toInt() + 1) {
                    val x = axisRatio * i
                    drawLine(
                        color = graphXAxisColor,
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                    )

                }
            }

            if(graphYAxisColor != Color.Unspecified && yAxisSplit > 0f) {
                val axisRatio: Float = size.height / yAxisSplit
                for (i in 0 until yAxisSplit.toInt() + 1) {
                    val y = axisRatio * i
                    drawLine(
                        color = graphYAxisColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                    )

                }
            }

            val samplingIntervalRatio =
                samplingInterval.inWholeMilliseconds / viewportDuration.inWholeMilliseconds.toFloat()
            drawRect(
                color = sampleBackgroundColor,
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
    endpointColor: Color,
    lineSegmentColor: Color,
    meterBeatLineColor: Color,
    currentTime: Long = System.currentTimeMillis(),
    viewportDuration: Duration = 60.seconds,
    viewportBPM: Int = 400,
    bpmRatio: Float = 0f,
    taps: List<TapRecord>? = null
) {

    Canvas(
        modifier = modifier
    ) {

        clipRect(
            0f, 0f, size.width, size.height
        ) {

            drawLine(
                color = meterBeatLineColor,
                start = Offset(0f, size.height * (1f - bpmRatio)),
                end = Offset(size.width, size.height * (1f - bpmRatio)),
            )

            taps ?: return@Canvas

            val xPlotRatio: Float = size.width / viewportDuration.inWholeMilliseconds
            taps.forEachIndexed { index, tap ->
                if(index > 0) {
                    val prevTap = taps[index - 1]
                    drawLine(
                        strokeWidth = 1f,
                        color = lineSegmentColor,
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
            }

            taps.forEach { tap ->
                drawCircle(
                    color = endpointColor,
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
    color: Color = Color.White,
    bpmRatio: Float = 0f
) {

    Canvas(
        modifier = modifier
    ) {

        clipRect(
            0f, 0f, size.width, size.height
        ) {

            drawLine(
                color = color,
                start = Offset(0f, size.height * (1f - bpmRatio)),
                end = Offset(size.width, size.height * (1f - bpmRatio)),
            )

        }
    }
}
