package com.tritiumgaming.feature.investigation.ui.tool.timers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.DigitalDreamTextStyle
import java.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun NotchedProgressBarTimer(
    modifier: Modifier = Modifier,
    bundle: NotchedProgressBarBundle,
    icon: @Composable (Modifier) -> Unit = {}
) {

    val minutes = bundle.state.remaining.milliseconds.inWholeMinutes
    val seconds = (bundle.state.remaining.milliseconds.inWholeSeconds % (minutes.toFloat())).toLong()

    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = bundle.title,
            color = bundle.colors.label,
            fontSize = 12.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                icon(Modifier)

                Text(
                    text = "$minutes:${"%02d".format(seconds)}",
                    color = bundle.colors.label,
                    style = DigitalDreamTextStyle.copy(
                        textAlign = TextAlign.Center
                    ),
                    fontSize = 12.sp
                )
            }

            Box(
                modifier = modifier
                    .fillMaxHeight(.5f)
                    .fillMaxWidth()
            ) {
                NotchedProgressBar(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    bundle = bundle
                )
            }

        }
    }
}

@Composable
internal fun NotchedProgressBar(
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
        val progress = state.remaining.toFloat() / maxDurationNormal
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
            topLeft = Offset((12f * scale), (12f * scale)),
            size = Size(
                width = (size.width - (24f * scale)) * progress,
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

internal data class NotchedProgressBarBundle(
    val title: String = "",
    val state: NotchedProgressBarUiState,
    val colors: NotchedProgressBarUiColors
)

internal data class NotchedProgressBarUiColors(
    val remaining: Color,
    val background: Color,
    val border: Color,
    val notch: Color,
    val label: Color
)

internal data class NotchedProgressBarUiState(
    val max: Long,
    val origin: Long = 0,
    val remaining: Long,
    val notches: List<ProgressBarNotch> = listOf(),
    val running: Boolean = false
)

internal data class ProgressBarNotch(
    val label: String,
    val xPos: Long
)
