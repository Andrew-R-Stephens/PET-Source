package com.tritiumgaming.feature.investigation.ui.common.footstep.beatline

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.clipRect

@Composable
internal fun BeatLine(
    modifier: Modifier = Modifier,
    state: BeatLineUiState,
    colors: BeatLineUiColors,
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
