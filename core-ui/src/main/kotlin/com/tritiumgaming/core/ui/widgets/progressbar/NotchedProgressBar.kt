package com.tritiumgaming.core.ui.widgets.progressbar

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer


@Composable
fun NotchedProgressBar(
    modifier: Modifier = Modifier,
    bundle: NotchedProgressBarBundle
) {
    val textMeasurer = rememberTextMeasurer()

    val state = bundle.state
    val colors = bundle.colors

    Canvas(
        modifier = modifier
    ) {
        val barHeight = size.height * 0.5f
        val labelsHeight = size.height * 0.5f

        val maxDurationNormal = state.max.coerceAtLeast(1).toFloat()
        val progressOffset = bundle.state.origin / maxDurationNormal
        val progressWidth = state.remaining.toFloat() / maxDurationNormal
        val scale = barHeight / 48f

        // --- Draw Progress Bar ---
        drawRoundRect(
            color = colors.background,
            topLeft = Offset((2f * scale), (2f * scale)),
            size = Size(
                width = size.width - (4f * scale),
                height = barHeight - (4f * scale)
            ),
            cornerRadius = CornerRadius(barHeight * .5f)
        )

        drawRoundRect(
            color = colors.remaining,
            topLeft = Offset( (size.width * progressOffset) + (12f * scale), (12f * scale)),
            size = Size(
                width = (size.width - (24f * scale)) * progressWidth,
                height = barHeight - (24f * scale)
            ),
            cornerRadius = CornerRadius(barHeight * .5f)
        )

        // Draw Notches
        val notchStrokeWidth = 3f * scale
        state.notches.forEach { notch ->
            val inverseNotch = maxDurationNormal - notch.xPos.toFloat()
            val progressAreaWidth = size.width - 24f * scale
            val notchX = 12f * scale +
                    (inverseNotch / maxDurationNormal) *
                    progressAreaWidth

            if (inverseNotch > 0 && inverseNotch < maxDurationNormal) {
                drawLine(
                    color = colors.notch,
                    start = Offset(notchX, (1f * scale)),
                    end = Offset(notchX, barHeight - (2f * scale)),
                    strokeWidth = notchStrokeWidth
                )
            }
        }

        drawRoundRect(
            color = colors.border,
            topLeft = Offset((2f * scale), (2f * scale)),
            size = Size(
                width = size.width - (4 * scale),
                height = barHeight - (4 * scale)
            ),
            cornerRadius = CornerRadius(barHeight * .5f),
            style = Stroke(width = 3f * scale)
        )

        // --- Draw Labels ---
        state.notches.forEach { notch ->
            val inverseNotch = (state.max.coerceAtLeast(1) - notch.xPos).toFloat()
            val progressAreaWidth = size.width - 24f * scale
            val notchX = 12f * scale +
                    (inverseNotch / state.max.coerceAtLeast(1)) *
                    progressAreaWidth

            val textLayoutResult = textMeasurer.measure(
                text = notch.label,
                style = TextStyle(
                    color = colors.label,
                    fontSize = (labelsHeight * .9f).toSp(),
                    fontWeight = FontWeight.Normal
                )
            )

            val textWidth = textLayoutResult.size.width.toFloat()
            val xPos = (notchX - textWidth / 2f).coerceIn(0f, size.width - textWidth)

            drawText(
                textLayoutResult = textLayoutResult,
                color = colors.label,
                topLeft = Offset(
                    x = xPos,
                    y = size.height - textLayoutResult.size.height + (labelsHeight * .1f)
                )
            )
        }
    }
}

data class NotchedProgressBarBundle(
    val title: String = "",
    val state: NotchedProgressBarUiState,
    val colors: NotchedProgressBarUiColors
)

data class NotchedProgressBarUiColors(
    val remaining: Color,
    val background: Color,
    val border: Color,
    val notch: Color,
    val label: Color
)

data class NotchedProgressBarUiState(
    val max: Long,
    val origin: Long = 0,
    val remaining: Long,
    val notches: List<ProgressBarNotch> = listOf(),
    val running: Boolean = false
)

data class ProgressBarNotch(
    val label: String,
    val xPos: Long
)
