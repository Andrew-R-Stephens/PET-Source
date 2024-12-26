package com.tritiumgaming.phasmophobiaevidencepicker.views.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
@Preview
fun InteractiveFootstep() {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        val sliderPosition = remember { mutableFloatStateOf(0f) }

        SpeedControl(sliderPosition)
        FootstepVisual(sliderPosition)
    }
}

@Composable
private fun SpeedControl(
    sliderPosition: MutableFloatState = mutableFloatStateOf(0f)
) {
    Slider(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        value = sliderPosition.floatValue,
        onValueChange = {
            sliderPosition.floatValue = it
        },
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.secondary,
            activeTrackColor = MaterialTheme.colorScheme.secondary,
            inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        valueRange = 1f..100f
    )
}

@Composable
private fun FootstepVisual(
    sliderPosition: MutableFloatState = mutableFloatStateOf(0f)
) {
    val oneSecond = 1000L
    val oneMinute = oneSecond * 60L

    val time =
            (oneMinute - (oneMinute * sliderPosition.floatValue * .01f))
                .toInt().coerceAtLeast(0)

    val animation =
        tween<Float>(
            durationMillis = time,
            easing = LinearEasing
        )

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val percentCompleted = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = animation,
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Column {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .sizeIn(48.dp, 96.dp)
                .padding(8.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                drawCircle(
                    Color.Red,
                    size.height.coerceAtMost(size.width * .5f),
                    Offset(
                        size.width * .5f,
                        size.height * .5f
                    ),
                    style = Stroke(16f)
                )

                indicator(percentCompleted.value)

            }
        }
        Text(text = "${sliderPosition.floatValue} ${time} ${animation}")
    }

}

private fun pointOnCircle(
    thetaInDegrees: Double, radius: Float, cX: Float, cY: Float,
): Offset {
    val x = cX + (radius * sin(Math.toRadians(thetaInDegrees)).toFloat())
    val y = cY + (radius * cos(Math.toRadians(thetaInDegrees)).toFloat())

    return Offset(x, y)
}

private fun DrawScope.indicator(
    percent: Float = 90f
) {
    val angle = 360f * percent

    val endOffset = pointOnCircle(
        thetaInDegrees = angle.toDouble(),
        radius = size.height / 2 - size.width,
        cX = center.x,
        cY = center.y
    )

    drawLine(
        color = Color.Magenta,
        start = center,
        end = endOffset,
        strokeWidth = 6.dp.toPx(),
        cap = StrokeCap.Round,
        alpha = 0.5f
    )
}