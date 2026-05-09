package com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels

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
import kotlin.time.Duration.Companion.seconds

@Composable
fun GraphLabelsXAxis(
    modifier: Modifier,
    colors: GraphLabelsUiColors,
    state: GraphLabelsUiState
) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(color = colors.label, fontSize = 10.sp)

    Canvas(modifier) {
        clipRect(
            size.width * -.25f,
            size.height * -.25f,
            size.width * 1.5f,
            size.height * 1.5f
        ) {
            val seconds = state.viewport / 1000f
            val labelStep = seconds / state.interval

            for (i in 0..state.interval.toInt()) {

                val time = labelStep * i

                val label = if (time % 1.0f == 0f) "${time.toInt()}s" else "${time}s"
                val textLayoutResult = textMeasurer.measure(
                    text = label,
                    style = textStyle,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Visible
                )

                val x = size.width - (size.width * (time / seconds))

                drawText(
                    textMeasurer = textMeasurer,
                    text = label,
                    style = textStyle,
                    topLeft = Offset(
                        (x - textLayoutResult.size.width.toFloat() * .5f)
                            .coerceIn(0f, size.width - textLayoutResult.size.width.toFloat()),
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
