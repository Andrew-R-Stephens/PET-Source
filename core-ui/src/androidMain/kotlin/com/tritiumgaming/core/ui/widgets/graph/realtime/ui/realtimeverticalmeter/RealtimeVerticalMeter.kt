package com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeverticalmeter

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Fill

@Composable
fun RealtimeVerticalMeter(
    modifier: Modifier = Modifier,
    colors: RealtimeVerticalMeterColors,
    state: RealtimeVerticalMeterUiState
) {
    Canvas(
        modifier = modifier
    ) {
        val predictiveRatioY = size.height * (1f - state.predictive)
        drawLine(
            color = colors.onColor,
            start = Offset(0f, predictiveRatioY),
            end = Offset(size.width, predictiveRatioY)
        )

        drawRect(
            style = Fill,
            color = colors.color,
            size = Size(size.width, size.height * state.current),
            topLeft = Offset(0f, size.height * (1f - state.current)),
        )
    }
}