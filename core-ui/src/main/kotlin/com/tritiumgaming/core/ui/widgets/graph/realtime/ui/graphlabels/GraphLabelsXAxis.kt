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
    colors: com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels.GraphLabelsUiColors,
    state: com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels.GraphLabelsUiState
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
            val seconds = (state.max / 1.seconds.inWholeMilliseconds).toInt()
            val labelCount = seconds / state.interval

            for (i in 0..state.interval.toInt()) {

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
