package com.tritiumgaming.feature.investigation.ui.common.footstep.graphlabels

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import kotlin.math.ceil

@Composable
internal fun GraphLabelsYAxis(
    modifier: Modifier,
    colors: GraphLabelsUiColors,
    state: GraphLabelsUiState
) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(color = colors.label, fontSize = 10.sp)

    val quantizedMax = ceil(state.viewport / state.interval) * state.interval
    val steps = (quantizedMax / state.interval).toInt()

    Canvas(modifier) {
        clipRect(
            size.width * -.25f,
            size.height * -.25f,
            size.width * 1.5f,
            size.height * 1.5f
        ) {
            for (step in 0..steps) {
                val bpm = step * state.interval
                val yRatio = bpm / quantizedMax
                val y = size.height - (size.height * yRatio)

                val mps = bpm / 60f

                val label = "${ mps.toInt() } m"

                drawLine(
                    color = colors.labelLine,
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