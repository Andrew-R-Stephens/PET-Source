package com.tritiumgaming.feature.footstepvisualizer.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.datastructs.CircularQueueLinkedList
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
@Preview
private fun Preview() {
    SelectiveTheme {
        FootstepVisualizer(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            alpha = .01f,
            maxDisplayBPM = 250f,
            viewportSeconds = 10.seconds,
            timeSplit = 10f,
            bpmSplit = 120f
        )
    }
}

@Composable
fun FootstepVisualizer(
    modifier: Modifier = Modifier,
    alpha: Float = .2f,
    maxDisplayBPM: Float = 400f,
    viewportSeconds: Duration = 60.seconds,
    timeSplit: Float = 10f,
    bpmSplit: Float = 120f
) {
    val now = System.currentTimeMillis()

    var instantBPM by remember { mutableFloatStateOf(0f) }
    var smoothedBPM by remember { mutableFloatStateOf(0f) }

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

            val potentialBPM = (60000f / delta)

            smoothedBPM = (alpha * instantBPM) + ((1f - alpha) * instantBPM)
            if(smoothedBPM > potentialBPM) smoothedBPM = potentialBPM

            if (smoothedBPM < 1f) smoothedBPM = 0f
        }

    }

    val onBeat = {
        if (lastTapTime != 0L) {
            val delta = now - lastTapTime

            instantBPM = (60000f / delta)

            taps.enqueue(TapRecord(now, instantBPM))
            if(taps.size > 100) {
                taps.dequeue()

                taps.peek()
            }

            smoothedBPM = if (smoothedBPM == 0f) { instantBPM }
                else { (alpha * instantBPM) + ((1f - alpha) * instantBPM) }

            if (smoothedBPM < 1f) smoothedBPM = 0f
        }

        lastTapTime = now
    }

    val bpmRatio = (smoothedBPM / maxDisplayBPM).coerceIn(0f, 1f)

    Column(modifier = modifier
        .clickable { onBeat() }
    ) {

        Row(modifier = Modifier
            .weight(1f)) {

            VerticalScale(
                modifier = Modifier
                    .width(24.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp),
                maxBpm = maxDisplayBPM,
                bpmSplit = bpmSplit
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                RealtimeGraph(
                    modifier = Modifier
                        .fillMaxSize(),
                    currentTime = System.currentTimeMillis(),
                    bpmLineRatio = bpmRatio,
                    viewportSeconds = viewportSeconds,
                    bpmViewport = maxDisplayBPM,
                    timeSplit = timeSplit,
                    taps = taps.asList()
                )
            }

            VerticalMeter(
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight(),
                color = if (bpmRatio > 0.8f) Color.Red else Color.Green,
                percent = bpmRatio
            )

        }

        HorizontalScale(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .padding(start = 24.dp, end = 12.dp),
            millisViewport = viewportSeconds.inWholeMilliseconds,
            timeSplit = timeSplit
        )

    }
}

private class TapRecord(
    val time: Long,
    val bpm: Float
)

/*
@Composable
fun FootstepVisualizer(
    modifier: Modifier = Modifier,
    alpha: Float = .2f,
    maxDisplayBPM: Float = 400f
) {
    val taps by remember {
        mutableStateOf(setOf(
            System.currentTimeMillis()
        ))
    }

    var lastTapTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var instantBPM by remember { mutableFloatStateOf(0f) }
    var smoothedBPM by remember { mutableFloatStateOf(0f) }

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
        val now = System.currentTimeMillis()
        val delta = now - lastTapTime

        if (lastTapTime != 0L && delta > 500f) {
            val delta = (now - lastTapTime)
            instantBPM = 60000f / delta
            smoothedBPM = ((alpha * instantBPM) + (1f - alpha) * instantBPM)

            if (smoothedBPM < 1f) smoothedBPM = 0f
        }

    }

    val onBeat = {
        val now = System.currentTimeMillis()
        if (lastTapTime != 0L) {
            val delta = now - lastTapTime
            instantBPM = 60000f / delta
            smoothedBPM = if (smoothedBPM == 0f) { instantBPM }
                else { (alpha * instantBPM) + (1f - alpha) * instantBPM }

        }
        lastTapTime = now
    }

    val percent = (smoothedBPM / maxDisplayBPM).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Row(modifier = modifier) {
            VerticalMeter(
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight(),
                color = if (percent > 0.8f) Color.Red else Color.Green,
                percent = percent
            )

            RealtimeGraph(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                percent = percent
            )

        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .background(Color.DarkGray)
                .clickable { onBeat() }
                .padding(16.dp),
            text = "BPM: ${smoothedBPM.toInt()}; ",
            color = Color.White
        )
    }
}
*/

@Composable
private fun VerticalMeter(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    percent: Float = 0f
) {
    Canvas(
        modifier = modifier
    ) {
        drawRect(
            style = Fill,
            color = color,
            size = Size(size.width, size.height * percent),
            topLeft = Offset(0f, size.height * (1f - percent)),
        )
    }
}


@Composable
private fun VerticalScale(
    modifier: Modifier,
    maxBpm: Float,
    bpmSplit: Float
) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(color = Color.Gray, fontSize = 10.sp)

    val quantizedMax = ceil(maxBpm / bpmSplit) * bpmSplit
    val steps = (quantizedMax / bpmSplit).toInt()

    Canvas(modifier) {
        clipRect(
            size.width * -.25f,
            size.height * -.25f,
            size.width * 1.5f,
            size.height
        ) {
            for (step in 0..steps + 1) {
                val bpm = step * bpmSplit
                val yRatio = bpm / quantizedMax
                val y = size.height - (size.height * yRatio)

                val mps = bpm / 60f

                val label = "${ mps.toInt() } m"

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
                    maxLines = 1
                )
            }
        }

    }
}

@Composable
private fun HorizontalScale(
    modifier: Modifier,
    millisViewport: Long,
    timeSplit: Float
) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(color = Color.Gray, fontSize = 10.sp)

    Canvas(modifier) {
        clipRect(
            size.width * -.25f,
            size.height * -.25f,
            size.width * 1.5f,
            size.height * 1.5f
        ) {
            val seconds = (millisViewport / 1.seconds.inWholeMilliseconds).toInt()
            val labelCount = seconds / timeSplit

            for (i in 0..timeSplit.toInt()) {

                val time = labelCount * i

                val label = "${time.toInt()}s"
                val x = size.width - (size.width * (time / seconds))

                drawText(
                    textMeasurer = textMeasurer,
                    text = label,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 10.sp
                    ),
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
private fun RealtimeGraph(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    currentTime: Long = System.currentTimeMillis(),
    viewportSeconds: Duration = 60.seconds,
    bpmViewport: Float = 400f,
    bpmLineRatio: Float = 0f,
    timeSplit: Float = 10f,
    taps: List<TapRecord>? = null
) {

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            clipRect(
                0f, 0f, size.width, size.height
            ) {
                val horizontalRatio: Float = size.width / timeSplit

                for (i in 0 until timeSplit.toInt() + 1) {

                    val x = horizontalRatio * i
                    drawLine(
                        color = Color.White.copy(alpha = .5f),
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                    )

                }
            }
        }

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            clipRect(
                0f, 0f, size.width, size.height
            ) {
                val xPlotRatio: Float = size.width / viewportSeconds.inWholeMilliseconds

                drawLine(
                    color = color,
                    start = Offset(0f, size.height * (1f - bpmLineRatio)),
                    end = Offset(size.width, size.height * (1f - bpmLineRatio)),
                )

                taps ?: return@Canvas

                taps.forEachIndexed { index, tap ->

                    if(index > 0) {
                        val prevTap = taps[index - 1]
                        drawLine(
                            strokeWidth = 1f,
                            color = Color.Red,
                            start = Offset(
                                x = size.width - ((currentTime - prevTap.time) * xPlotRatio),
                                y = size.height * (1f - (prevTap.bpm / bpmViewport))
                            ),
                            end = Offset(
                                x = size.width - ((currentTime - tap.time) * xPlotRatio),
                                y = size.height * (1f - (tap.bpm / bpmViewport))
                            )
                        )
                    }
                }

                taps.forEach { tap ->
                    drawCircle(
                        color = Color.Red,
                        radius = 4f,
                        center = Offset(
                            x = size.width - ((currentTime - tap.time) * xPlotRatio),
                            y = size.height * (1f - (tap.bpm / bpmViewport))
                        )
                    )
                }
            }
        }
    }
}
