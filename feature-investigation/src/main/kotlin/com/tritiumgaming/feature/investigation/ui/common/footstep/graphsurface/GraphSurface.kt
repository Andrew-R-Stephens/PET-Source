package com.tritiumgaming.feature.investigation.ui.common.footstep.graphsurface

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect


@Composable
internal fun GraphSurface(
    modifier: Modifier = Modifier,
    graphSurfaceUiColors: GraphSurfaceUiColors,
    graphSurfaceUiState: GraphSurfaceUiState
) {
    val xInterval = graphSurfaceUiState.domainInterval
    val yInterval = graphSurfaceUiState.rangeInterval
    val viewportDuration: Long = graphSurfaceUiState.domain
    val samplingInterval: Long = graphSurfaceUiState.subDomain

    Canvas(
        modifier = modifier
            .background(graphSurfaceUiColors.surface)
    ) {
        clipRect(
            0f, 0f, size.width, size.height
        ) {
            val samplingIntervalRatio =
                samplingInterval / viewportDuration.toFloat()
            drawRect(
                color = graphSurfaceUiColors.surfaceContainer,
                topLeft = Offset(
                    x = size.width - (size.width * samplingIntervalRatio),
                    y = 0f
                ),
                size = Size(
                    width = size.width,
                    height = size.height
                )
            )

            if(graphSurfaceUiColors.xAxis != Color.Unspecified && xInterval > 0f) {
                val axisRatio: Float = size.width / xInterval
                for (i in 0 until xInterval.toInt() + 1) {
                    val x = axisRatio * i
                    drawLine(
                        color = graphSurfaceUiColors.xAxis,
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                    )

                }
            }

            if(graphSurfaceUiColors.yAxis != Color.Unspecified && yInterval > 0f) {
                val axisRatio: Float = size.height / yInterval
                for (i in 0 until yInterval.toInt() + 1) {
                    val y = axisRatio * i
                    drawLine(
                        color = graphSurfaceUiColors.yAxis,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                    )

                }
            }

        }
    }
}
