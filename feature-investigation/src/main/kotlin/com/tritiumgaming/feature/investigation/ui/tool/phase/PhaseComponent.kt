package com.tritiumgaming.feature.investigation.ui.tool.phase


import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.feature.investigation.app.mappers.phase.toPhaseTitle
import com.tritiumgaming.feature.investigation.app.mappers.phase.toStringResource
import com.tritiumgaming.shared.data.investigation.model.PhaseData.Companion.DEFAULT
import com.tritiumgaming.shared.data.investigation.model.PhaseData.Companion.DURATION_30_SECONDS
import com.tritiumgaming.shared.data.phase.model.PhaseResources.PhaseIdentifier


@Composable
internal fun PhaseComponent(
    modifier: Modifier = Modifier,
    state: PhaseUiState
) {
    val isAlert = state.type == PhaseIdentifier.HUNT
    val canAnimate = state.canFlash

    Surface(
        modifier = modifier
            .then(
                when {
                    canAnimate ->
                        Modifier
                            .phaseBorderAnimation(PhaseAnimationType.THICKNESS)
                            .phaseBorderAnimation(PhaseAnimationType.PULSE_INWARD)
                    isAlert ->
                        Modifier
                            .phaseBorderStatic(PhaseAnimationType.PULSE)
                            .phaseBorderStatic(PhaseAnimationType.PULSE_INWARD)
                    else -> Modifier
                }
            ),
        color = LocalPalette.current.surfaceContainerLowest,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .padding(8.dp)
                .basicMarquee(Integer.MAX_VALUE, MarqueeAnimationMode.Immediately),
            text = stringResource(state.type.toPhaseTitle().toStringResource()),
            color = LocalPalette.current.onSurface,
            style = LocalTypography.current.tertiary.regular.copy(
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

internal data class PhaseUiState(
    internal val type: PhaseIdentifier = PhaseIdentifier.SETUP,
    internal val animationType: PhaseAnimationType = PhaseAnimationType.PULSE_INWARD,
    internal val canAlertAudio: Boolean = false,
    internal val canFlash: Boolean = true,
    internal val startFlashTime: Long = DEFAULT,
    internal val elapsedFlashTime: Long = DEFAULT,
    internal val maxFlashTime: Long = DURATION_30_SECONDS,
)

internal enum class PhaseAnimationType {
    PULSE,         // Smooth alpha pulsing
    PULSE_INWARD,  // Radiating primary color inwards
    FLASH,         // Hard blinking
    THICKNESS,     // Border width oscillation
    COLOR,         // Hue/Color shifting
    DASH           // Moving dashed border
}

@Composable
private fun Modifier.phaseBorderAnimation(
    type: PhaseAnimationType
): Modifier {

    val infiniteTransition = rememberInfiniteTransition(label = "PhaseBorderAnimation")
    val primaryColor = LocalPalette.current.primary

    return when (type) {
        PhaseAnimationType.PULSE -> {
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.5f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "PulseAlpha"
            )
            this.drawWithContent {
                drawContent()
                val sw = 2.dp.toPx()
                if (sw >= size.width || sw >= size.height) return@drawWithContent

                val cornerRadiusPx = 8.dp.toPx()
                inset(sw / 2f) {
                    drawRoundRect(
                        color = primaryColor.copy(alpha = alpha),
                        cornerRadius = CornerRadius(cornerRadiusPx - sw / 2f),
                        style = Stroke(width = sw)
                    )
                }
            }
        }

        PhaseAnimationType.PULSE_INWARD -> {
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "PulseInwardAlpha"
            )
            this.drawWithContent {
                drawContent()
                val cornerRadiusPx = 8.dp.toPx()
                val steps = 16
                val sw = 0.5.dp.toPx()
                val maxInsetWidth = size.width / 2f
                val maxInsetHeight = size.height / 2f

                for (i in 0 until steps) {
                    val currentInset = i * sw + sw / 2f + 2.dp.toPx()
                    if (currentInset >= maxInsetWidth || currentInset >= maxInsetHeight) break

                    val layerAlpha = alpha * (1f - i.toFloat() / steps) * 0.4f
                    if (layerAlpha <= 0.01f) continue

                    inset(currentInset) {
                        drawRoundRect(
                            color = primaryColor.copy(alpha = layerAlpha),
                            cornerRadius = CornerRadius((cornerRadiusPx - currentInset).coerceAtLeast(0f)),
                            style = Stroke(width = sw)
                        )
                    }
                }
            }
        }

        PhaseAnimationType.FLASH -> {
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 500
                        0f at 0
                        0f at 249
                        1f at 250
                        1f at 500
                    },
                    repeatMode = RepeatMode.Restart
                ),
                label = "FlashAlpha"
            )
            this.drawWithContent {
                drawContent()
                val sw = 2.dp.toPx()
                if (sw >= size.width || sw >= size.height) return@drawWithContent

                val cornerRadiusPx = 8.dp.toPx()
                inset(sw / 2f) {
                    drawRoundRect(
                        color = primaryColor.copy(alpha = alpha),
                        cornerRadius = CornerRadius(cornerRadiusPx - sw / 2f),
                        style = Stroke(width = sw)
                    )
                }
            }
        }

        PhaseAnimationType.THICKNESS -> {
            val width by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "ThicknessWidth"
            )
            this.drawWithContent {
                drawContent()
                val sw = width.dp.toPx()
                if (sw <= 0.1f) return@drawWithContent
                if (sw >= size.width || sw >= size.height) return@drawWithContent

                val cornerRadiusPx = 8.dp.toPx()
                inset(sw / 2f) {
                    drawRoundRect(
                        color = primaryColor,
                        cornerRadius = CornerRadius(cornerRadiusPx - sw / 2f),
                        style = Stroke(width = sw)
                    )
                }
            }
        }

        PhaseAnimationType.COLOR -> {
            val color by infiniteTransition.animateColor(
                initialValue = primaryColor,
                targetValue = LocalPalette.current.onSurface.copy(alpha = 0.5f),
                animationSpec = infiniteRepeatable(
                    animation = tween(1500),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "ColorShift"
            )
            this.drawWithContent {
                drawContent()
                val sw = 2.dp.toPx()
                if (sw >= size.width || sw >= size.height) return@drawWithContent

                val cornerRadiusPx = 8.dp.toPx()
                inset(sw / 2f) {
                    drawRoundRect(
                        color = color,
                        cornerRadius = CornerRadius(cornerRadiusPx - sw / 2f),
                        style = Stroke(width = sw)
                    )
                }
            }
        }

        PhaseAnimationType.DASH -> {
            val phase by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 40f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "DashPhase"
            )
            this.drawWithContent {
                drawContent()
                val sw = 2.dp.toPx()
                if (sw >= size.width || sw >= size.height) return@drawWithContent

                val cornerRadiusPx = 8.dp.toPx()
                inset(sw / 2f) {
                    drawRoundRect(
                        color = primaryColor,
                        cornerRadius = CornerRadius(cornerRadiusPx - sw / 2f),
                        style = Stroke(
                            width = sw,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), phase)
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun Modifier.phaseBorderStatic(
    type: PhaseAnimationType
): Modifier {

    val infiniteTransition = rememberInfiniteTransition(label = "PhaseBorderAnimation")
    val primaryColor = LocalPalette.current.primary

    return when (type) {
        PhaseAnimationType.PULSE -> {
            this.drawWithContent {
                drawContent()
                val sw = 2.dp.toPx()
                if (sw >= size.width || sw >= size.height) return@drawWithContent

                val cornerRadiusPx = 8.dp.toPx()
                inset(sw / 2f) {
                    drawRoundRect(
                        color = primaryColor,
                        cornerRadius = CornerRadius(cornerRadiusPx - sw / 2f),
                        style = Stroke(width = sw)
                    )
                }
            }
        }

        PhaseAnimationType.PULSE_INWARD -> {
            this.drawWithContent {
                drawContent()
                val cornerRadiusPx = 8.dp.toPx()
                val steps = 16
                val sw = 0.5.dp.toPx()
                val maxInsetWidth = size.width / 2f
                val maxInsetHeight = size.height / 2f

                for (i in 0 until steps) {
                    val currentInset = i * sw + sw / 2f + 2.dp.toPx()
                    if (currentInset >= maxInsetWidth || currentInset >= maxInsetHeight) break

                    val layerAlpha = (1f - i.toFloat() / steps) * 0.4f
                    if (layerAlpha <= 0.01f) continue

                    inset(currentInset) {
                        drawRoundRect(
                            color = primaryColor.copy(alpha = layerAlpha),
                            cornerRadius = CornerRadius((cornerRadiusPx - currentInset).coerceAtLeast(0f)),
                            style = Stroke(width = sw)
                        )
                    }
                }
            }
        }

        PhaseAnimationType.FLASH -> {
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 500
                        0f at 0
                        0f at 249
                        1f at 250
                        1f at 500
                    },
                    repeatMode = RepeatMode.Restart
                ),
                label = "FlashAlpha"
            )
            this.drawWithContent {
                drawContent()
                val sw = 2.dp.toPx()
                if (sw >= size.width || sw >= size.height) return@drawWithContent

                val cornerRadiusPx = 8.dp.toPx()
                inset(sw / 2f) {
                    drawRoundRect(
                        color = primaryColor.copy(alpha = alpha),
                        cornerRadius = CornerRadius(cornerRadiusPx - sw / 2f),
                        style = Stroke(width = sw)
                    )
                }
            }
        }

        PhaseAnimationType.THICKNESS -> {
            val width by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "ThicknessWidth"
            )
            this.drawWithContent {
                drawContent()
                val sw = width.dp.toPx()
                if (sw <= 0.1f) return@drawWithContent
                if (sw >= size.width || sw >= size.height) return@drawWithContent

                val cornerRadiusPx = 8.dp.toPx()
                inset(sw / 2f) {
                    drawRoundRect(
                        color = primaryColor,
                        cornerRadius = CornerRadius(cornerRadiusPx - sw / 2f),
                        style = Stroke(width = sw)
                    )
                }
            }
        }

        PhaseAnimationType.COLOR -> {
            val color by infiniteTransition.animateColor(
                initialValue = primaryColor,
                targetValue = LocalPalette.current.onSurface.copy(alpha = 0.5f),
                animationSpec = infiniteRepeatable(
                    animation = tween(1500),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "ColorShift"
            )
            this.drawWithContent {
                drawContent()
                val sw = 2.dp.toPx()
                if (sw >= size.width || sw >= size.height) return@drawWithContent

                val cornerRadiusPx = 8.dp.toPx()
                inset(sw / 2f) {
                    drawRoundRect(
                        color = color,
                        cornerRadius = CornerRadius(cornerRadiusPx - sw / 2f),
                        style = Stroke(width = sw)
                    )
                }
            }
        }

        PhaseAnimationType.DASH -> {
            val phase by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 40f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "DashPhase"
            )
            this.drawWithContent {
                drawContent()
                val sw = 2.dp.toPx()
                if (sw >= size.width || sw >= size.height) return@drawWithContent

                val cornerRadiusPx = 8.dp.toPx()
                inset(sw / 2f) {
                    drawRoundRect(
                        color = primaryColor,
                        cornerRadius = CornerRadius(cornerRadiusPx - sw / 2f),
                        style = Stroke(
                            width = sw,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), phase)
                        )
                    )
                }
            }
        }
    }
}
