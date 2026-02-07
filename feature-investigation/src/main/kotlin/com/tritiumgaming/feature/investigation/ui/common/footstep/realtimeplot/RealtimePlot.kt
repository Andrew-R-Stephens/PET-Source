package com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeplot

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import kotlin.collections.forEach


@Composable
internal fun RealtimePlot(
    modifier: Modifier = Modifier,
    realtimePlotUiState: RealtimePlotUiState,
    realtimePlotUiColors: RealtimePlotUiColors,
) {

    val now = realtimePlotUiState.currentTime

    val viewportY = realtimePlotUiState.viewportYInterval
    val viewportX = realtimePlotUiState.viewportXInterval

    val taps = realtimePlotUiState.taps

    Canvas(
        modifier = modifier
    ) {

        clipRect(
            0f, 0f, size.width, size.height
        ) {

            taps ?: return@Canvas

            val xPlotRatio: Float = size.width / viewportX

            if (taps.isNotEmpty()) {
                val instantPath = Path()
                val averagePath = Path()
                val weightedAveragePath = Path()

                val firstTap = taps.first()

                val startX = size.width - ((now - firstTap.pX) * xPlotRatio)
                val startY0 = size.height * (1f - (firstTap.pY / viewportY))
                val startY1 = size.height * (1f - (firstTap.avg / viewportY))
                val startY2 = size.height * (1f - (firstTap.weightedAvg / viewportY))

                instantPath.moveTo(startX, startY0)
                averagePath.moveTo(startX, startY1)
                weightedAveragePath.moveTo(startX, startY2)

                for (i in 1 until taps.size) {
                    val prevTap = taps[i - 1]
                    val currTap = taps[i]

                    val x1 = size.width - ((now - prevTap.pX) * xPlotRatio)
                    val y10 = size.height * (1f - (prevTap.pY / viewportY))
                    val y11 = size.height * (1f - (prevTap.avg / viewportY))
                    val y12 = size.height * (1f - (prevTap.weightedAvg / viewportY))
                    val x2 = size.width - ((now - currTap.pX) * xPlotRatio)
                    val y20 = size.height * (1f - (currTap.pY / viewportY))
                    val y21 = size.height * (1f - (currTap.avg / viewportY))
                    val y22 = size.height * (1f - (currTap.weightedAvg / viewportY))

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
                    color = realtimePlotUiColors.instant
                )

                drawPath(
                    path = weightedAveragePath,
                    style = Stroke(width = 2f),
                    color = realtimePlotUiColors.weighted
                )

                drawPath(
                    path = averagePath,
                    style = Stroke(width = 2f),
                    color = realtimePlotUiColors.smoothed
                )
            }

            taps.forEach { tap ->
                drawCircle(
                    color = realtimePlotUiColors.instant,
                    radius = 4f,
                    center = Offset(
                        x = size.width - ((now - tap.pX) * xPlotRatio),
                        y = size.height * (1f - (tap.pY / viewportY.toFloat()))
                    )
                )
            }
        }
    }
}
