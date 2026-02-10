package com.tritiumgaming.core.ui.widgets.graph.realtime.ui.beatline

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.clipRect

@Composable
fun BeatLine(
    modifier: Modifier = Modifier,
    state: com.tritiumgaming.core.ui.widgets.graph.realtime.ui.beatline.BeatLineUiState,
    colors: com.tritiumgaming.core.ui.widgets.graph.realtime.ui.beatline.BeatLineUiColors,
) {

    Canvas(
        modifier = modifier
    ) {

        clipRect(
            0f, 0f, size.width, size.height
        ) {

            drawLine(
                color = colors.color,
                start = Offset(0f, size.height * (1f - state.smoothedBPM)),
                end = Offset(size.width, size.height * (1f - state.smoothedBPM)),
            )

        }
    }
}
