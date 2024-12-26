package com.tritiumgaming.phasmophobiaevidencepicker.views.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
@Preview
fun Test() {
    InvestigationFootstepComposable(1f)
}

@Composable
fun InvestigationFootstepComposable(
    metersPerSecond: Float = 0f
) {
    var currentBPM by remember {
        mutableFloatStateOf(metersPerSecond)
    }

    // Allow resume on rotation
    var currentThetaStep by remember { mutableFloatStateOf(0f) }
    var currentTheta by remember { mutableFloatStateOf(0f) }
    val seconds = remember { Animatable(currentBPM) }

    var firstTimeMillis by remember { mutableLongStateOf (System.currentTimeMillis()) }
    var rotations by remember { mutableIntStateOf(0) }

    var savedTimeMillis = firstTimeMillis

    val isPlaying by remember { mutableStateOf(true) }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            // Infinite repeatable rotation when is playing
            seconds.animateTo(
                targetValue = 1000f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                val currentTimeMillis = System.currentTimeMillis()
                val currentTimeDifference = (currentTimeMillis - savedTimeMillis).toFloat()
                savedTimeMillis = currentTimeMillis

                currentThetaStep = currentTimeDifference * currentBPM * 360f / 1000f

                currentTheta += currentThetaStep
                if(currentTheta >= 360f) {
                    rotations++
                }
                currentTheta %= 360f
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .background(Color.Red)
    ) {

        val strokeWidth = 20f
        val circleSize = 64.dp

        Box(
            modifier = Modifier
                .padding(8.dp)
                .height(circleSize)
                .aspectRatio(1f)
        ) {

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawCircle(
                    Color.Blue,
                    radius = (64 * 2) - (strokeWidth * 2.5f),
                    style = Stroke(
                        width = strokeWidth
                    ),
                    alpha = .8f
                )

                val indicatorLength = 8.dp
                val indicatorOffset = 0.dp

                val startOffset = pointOnCircle(
                    //thetaInDegrees = currentRotation.toDouble(),
                    thetaInDegrees = currentTheta.toDouble(),
                    // Offset from the center to start drawing the markers
                    radius = size.height / 2 - indicatorOffset.toPx(),
                    cX = center.x,
                    cY = center.y
                )

                // Small indicator marker
                val endOffset = pointOnCircle(
                    thetaInDegrees = currentTheta.toDouble(),
                    // Length of small indicator
                    radius = size.height / 2 - indicatorLength.toPx(),
                    cX = center.x,
                    cY = center.y
                )

                // Draw the small indicator marker using a thin line
                speedMarker(
                    startOffset, endOffset,
                    SolidColor(Color.Cyan),
                    5.dp.toPx()
                )

            }

        }

        Column {
            Slider(
                value = currentBPM,
                valueRange = .4f..2.5f,
                onValueChange = {
                    currentBPM = it
                    firstTimeMillis = System.currentTimeMillis()
                    rotations = 0
                    currentTheta = 0f
                },
                steps = ((2.5f - .4f) / .01f).toInt()
            )

            Text(
                text = "BPM: ${String.format("%.1f", currentBPM)} " +
                        "Step: ${String.format("%.1f", currentThetaStep)} " +
                        "Theta: ${currentTheta.toInt()}"
            )

            val seconds = String.format("%.1f", (System.currentTimeMillis() - firstTimeMillis) / 1000f)
            Text(
                text = "S: $seconds " +
                        "R: $rotations"
            )
        }
    }
}

private fun pointOnCircle(
    thetaInDegrees: Double,
    radius: Float,
    cX: Float,
    cY: Float,
): Offset {
    val x = cX + (radius * sin(Math.toRadians(thetaInDegrees)).toFloat())
    val y = cY + (radius * cos(Math.toRadians(thetaInDegrees)).toFloat())

    return Offset(x, y)
}

private fun DrawScope.speedMarker(
    startPoint: Offset,
    endPoint: Offset,
    brush: Brush,
    strokeWidth: Float
) {
    drawLine(brush = brush, start = startPoint,
        cap = StrokeCap.Round,
        end = endPoint, strokeWidth = strokeWidth)
}