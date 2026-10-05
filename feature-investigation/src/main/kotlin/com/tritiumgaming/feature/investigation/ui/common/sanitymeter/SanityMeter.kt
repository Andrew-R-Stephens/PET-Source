package com.tritiumgaming.feature.investigation.ui.common.sanitymeter

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.ColorUtils
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography

@Composable
internal fun SanityMeter(
    modifier: Modifier = Modifier,
    sanityLevel: Float,
    showText: Boolean,
    showProgress: Boolean
) {
    val palette = LocalPalette.current
    val typography = LocalTypography.current

    val skullPainter = painterResource(R.drawable.icon_sanityhead_skull)
    val brainPainter = painterResource(R.drawable.icon_sanityhead_brain)
    val borderPainter = painterResource(R.drawable.icon_sanityhead_border)

    val textMeasurer = rememberTextMeasurer()

    Spacer(
        modifier = modifier
            .aspectRatio(1f)
            .drawWithCache {
                val pieStartColor = palette.onSurface.toArgb()
                val pieEndColor = palette.error.toArgb()
                val pieColor = Color(ColorUtils.interpolate(pieStartColor, pieEndColor, sanityLevel))

                val skullColor = palette.surfaceContainer
                val brainStartColor = Color.Gray.toArgb()
                val brainEndColor = palette.error.toArgb()
                val brainColor = Color(ColorUtils.interpolate(brainStartColor, brainEndColor, sanityLevel))
                val borderColor = palette.onSurface

                val skullFilter = ColorFilter.tint(skullColor)
                val brainFilter = ColorFilter.tint(brainColor)
                val borderFilter = ColorFilter.tint(borderColor)

                val percentageText = sanityLevel.toPercentageString()
                val fontSize = 14.sp
                val outlineTextStyle = typography.tertiary.bold.copy(
                    color = palette.scrim,
                    textAlign = TextAlign.Center,
                    fontSize = fontSize,
                    drawStyle = Stroke(width = 3f, join = StrokeJoin.Round)
                )
                val fillTextStyle = typography.tertiary.bold.copy(
                    color = palette.primary,
                    textAlign = TextAlign.Center,
                    fontSize = fontSize
                )

                onDrawBehind {
                    if (showProgress) {
                        drawArc(
                            color = pieColor,
                            startAngle = -90f,
                            sweepAngle = sanityLevel * 360f,
                            useCenter = true
                        )
                        val strokeWidth = 1.dp.toPx()
                        drawCircle(
                            color = palette.onSurface,
                            radius = (size.minDimension - strokeWidth) / 2f,
                            style = Stroke(width = strokeWidth)
                        )
                    }

                    val scale = if (showProgress) 0.7f else 1.0f
                    val layerWidth = size.width * scale
                    val layerHeight = size.height * scale
                    val layerSize = Size(layerWidth, layerHeight)
                    val offsetX = (size.width - layerWidth) / 2f
                    val offsetY = (size.height - layerHeight) / 2f

                    translate(left = offsetX, top = offsetY) {
                        with(skullPainter) {
                            draw(layerSize, colorFilter = skullFilter)
                        }
                        with(brainPainter) {
                            draw(layerSize, colorFilter = brainFilter)
                        }
                        with(borderPainter) {
                            draw(layerSize, colorFilter = borderFilter)
                        }
                    }

                    if (showText) {
                        val outlineResult = textMeasurer.measure(
                            text = percentageText,
                            style = outlineTextStyle,
                            maxLines = 1
                        )
                        val fillResult = textMeasurer.measure(
                            text = percentageText,
                            style = fillTextStyle,
                            maxLines = 1
                        )

                        val textX = (size.width - fillResult.size.width) / 2f
                        val textY = (size.height - fillResult.size.height) / 2f

                        drawText(
                            textLayoutResult = outlineResult,
                            topLeft = Offset(textX, textY)
                        )
                        drawText(
                            textLayoutResult = fillResult,
                            topLeft = Offset(textX, textY)
                        )
                    }
                }
            }
    )
}

@Preview
@Composable
private fun SanityMeterPreview() {
    LocalThemeProvider {
        SanityMeter(
            modifier = Modifier.size(120.dp),
            sanityLevel = 0.75f,
            showText = false,
            showProgress = false
        )
    }
}


