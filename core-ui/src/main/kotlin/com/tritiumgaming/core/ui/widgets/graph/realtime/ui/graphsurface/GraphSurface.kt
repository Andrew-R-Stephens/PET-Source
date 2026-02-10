package com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphsurface

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect


@Composable
fun GraphSurface(
    modifier: Modifier = Modifier,
    colors: GraphSurfaceUiColors,
    state: GraphSurfaceUiState
) {
    val xInterval = state.domainInterval
    val yInterval = state.rangeInterval
    val viewportDuration: Long = state.domain
    val samplingInterval: Long = state.domainSampleInterval

    Canvas(
        modifier = modifier
            .background(colors.surface)
    ) {
        clipRect(
            0f, 0f, size.width, size.height
        ) {
            val samplingIntervalRatio =
                samplingInterval / viewportDuration.toFloat()
            drawRect(
                color = colors.surfaceContainer,
                topLeft = Offset(
                    x = size.width - (size.width * samplingIntervalRatio),
                    y = 0f
                ),
                size = Size(
                    width = size.width,
                    height = size.height
                )
            )

            if(colors.domain != Color.Unspecified && xInterval > 0f) {
                val axisRatio: Float = size.width / xInterval
                for (i in 0 until xInterval.toInt() + 1) {
                    val x = axisRatio * i
                    drawLine(
                        color = colors.domain,
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                    )

                }
            }

            if(colors.range != Color.Unspecified && yInterval > 0f) {
                val axisRatio: Float = size.height / yInterval
                for (i in 0 until yInterval.toInt() + 1) {
                    val y = axisRatio * i
                    drawLine(
                        color = colors.range,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                    )

                }
            }

        }
    }
}
