package com.tritiumgaming.feature.footstepvisualizer.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.common.util.datastructs.LinearQueueLinkedList
import com.tritiumgaming.core.common.util.datastructs.Node
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import kotlin.time.Duration.Companion.seconds

@Composable
@Preview
private fun Preview() {
    SelectiveTheme {
        FootstepVisualizer(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            alpha = .9f,
            maxDisplayBPM = 400f
        )
    }
}

@Composable
fun FootstepVisualizer(
    modifier: Modifier = Modifier,
    alpha: Float = .2f,
    maxDisplayBPM: Float = 400f
) {
    var taps by remember {
        mutableStateOf(
            LinearQueueLinkedList<TapRecord>()
        )
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

        if (lastTapTime != 0L/* && delta > 1000f*/) {
            val delta = (now - lastTapTime)
            instantBPM = 60000f / delta
            smoothedBPM = ((alpha * instantBPM) + (1f - alpha) * smoothedBPM)

            if (smoothedBPM < 1f) smoothedBPM = 0f
        }
    }

    val onBeat = {
        val now = System.currentTimeMillis()
        if (lastTapTime != 0L) {
            val delta = now - lastTapTime
            instantBPM = 60000f / delta

            taps.enqueue(TapRecord(now, instantBPM))

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
                bpmLineRatio = percent,
                millisViewport = 10.seconds.inWholeMilliseconds,
                bpmViewport = 400f,
                taps = taps
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
private fun RealtimeGraph(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    millisViewport: Long = 60.seconds.inWholeMilliseconds,
    bpmViewport: Float = 400f,
    bpmLineRatio: Float = 0f,
    taps: LinearQueueLinkedList<TapRecord>? = null
) {

    val now = System.currentTimeMillis()

    Canvas(
        modifier = modifier
    ) {
        val xPlotRatio: Float = size.width / millisViewport
        val yPlotRatio: Float = size.height / bpmLineRatio

        drawLine(
            color = color,
            start = Offset(0f, size.height * (1f - bpmLineRatio)),
            end = Offset(size.width, size.height * (1f - bpmLineRatio)),
        )

        taps ?: return@Canvas

        var current: Node<TapRecord>? = taps.head

        for(i: Int in 0..taps.size) {
            val data = current?.data ?: return@Canvas

            drawCircle(
                color = Color.Red,
                radius = 4f,
                center = Offset(
                    x = size.width - ((now - data.time) * xPlotRatio),
                    y = size.height * (1f - (data.bpm / bpmViewport))
                )
            )
            current = current.next
        }

    }
}
