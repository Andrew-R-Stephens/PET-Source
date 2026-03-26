package com.tritiumgaming.feature.investigation.ui.common.sanitymeter

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.ColorUtils
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import java.util.Locale

@Composable
fun SanityMeter(
    modifier: Modifier = Modifier,
    sanityUiState: PlayerSanityUiState,
    showText: Boolean = false
) {
    val sanityPercent = sanityUiState.sanityLevel

    val sanityPercentString = String.format(Locale.ROOT, "%d%%",
        (sanityUiState.sanityLevel * 100).toInt())

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .size(12.dp)
            .border(
                1.dp,
                LocalPalette.current.onSurface,
                CircleShape
            )
    ) {
        SanityPie(
            startColor = LocalPalette.current.error.copy(alpha= 0f).toArgb(),
            endColor = LocalPalette.current.error.toArgb(),
            interpolation = sanityPercent
        )

        Box(
            modifier = Modifier
                .fillMaxSize(.7f)
                .align(Alignment.Center)
        ) {

            SanityImageLayer(
                image = R.drawable.icon_sanityhead_skull,
                startColor = LocalPalette.current.onSurface.toArgb(),
                endColor = LocalPalette.current.onSurface.toArgb(),
                interpolation = sanityPercent
            )
            SanityImageLayer(
                image = R.drawable.icon_sanityhead_brain,
                startColor = (Color.Gray).toArgb(),
                endColor = LocalPalette.current.error.toArgb(),
                interpolation = sanityPercent
            )
            SanityImageLayer(
                image = R.drawable.icon_sanityhead_border,
                startColor = LocalPalette.current.onSurface.toArgb(),
                endColor = LocalPalette.current.onSurface.toArgb(),
                interpolation = sanityPercent
            )

            if(showText) {
                val textMeasurer = rememberTextMeasurer()
                val fontSize = 14.sp
                val textStyle = LocalTypography.current.tertiary.bold.copy(
                    color = LocalPalette.current.scrim,
                    textAlign = TextAlign.Center,
                    fontSize = fontSize,
                )

                Text(
                    modifier = Modifier
                        .fillMaxSize(.75f)
                        .wrapContentHeight()
                        .align(Alignment.Center)
                        .drawBehind {
                            val textLayoutResult = textMeasurer.measure(
                                text = sanityPercentString,
                                style = textStyle
                            )
                            val xOffset = (size.width - textLayoutResult.size.width) * .5f
                            val centerOffset = Offset(xOffset, 0f)

                            drawText(
                                textMeasurer = textMeasurer,
                                text = sanityPercentString,
                                topLeft = centerOffset,
                                maxLines = 1,
                                style = textStyle.copy(
                                    drawStyle = Stroke(
                                        width = 3f,
                                        join = StrokeJoin.Round
                                    ),
                                )
                            )
                        },
                    text = sanityPercentString,
                    maxLines = 1,
                    style = textStyle,
                    color = LocalPalette.current.primary,
                    fontSize = fontSize,
                )
            }

        }

    }
}

@Composable
fun SanityImageLayer(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int = R.drawable.icon_sanityhead_brain,
    @ColorInt startColor: Int,
    @ColorInt endColor: Int,
    interpolation: Float = 1f
) {
    SanityImageLayer(
        modifier = modifier
            .fillMaxSize(),
        image = image,
        tint = ColorUtils.interpolate(startColor, endColor, interpolation)
    )
}

@Composable
fun SanityImageLayer(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int = R.drawable.icon_sanityhead_brain,
    @ColorInt tint: Int
) {
    Image(
        modifier = modifier
            .fillMaxSize(),
        painter = painterResource(id = image),
        contentDescription = "",
        colorFilter = ColorFilter.tint(
            Color(tint),
            BlendMode.SrcIn)
    )
}

@Composable
fun SanityPie(
    modifier: Modifier = Modifier,
    @ColorInt startColor: Int,
    @ColorInt endColor: Int,
    interpolation: Float = 1f
) {
    Box(
        modifier = modifier
            .drawBehind {
                drawArc(
                    Color(
                        ColorUtils.interpolate(
                            startColor,
                            endColor,
                            interpolation
                        )
                    ),
                    startAngle = -90f,
                    sweepAngle = interpolation * 360f,
                    useCenter = true
                )
            }
            .aspectRatio(1f)
            .fillMaxSize()
    )
}


@Composable
@Preview
private fun PreviewSanityPieEmpty() {
    SelectiveTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SanityMeter(modifier = Modifier.size(96.dp),
                sanityUiState = PlayerSanityUiState(sanityLevel = 1f))
            SanityMeter(modifier = Modifier.size(96.dp),
                sanityUiState = PlayerSanityUiState(sanityLevel = .75f))
            SanityMeter(modifier = Modifier.size(96.dp),
                sanityUiState = PlayerSanityUiState(sanityLevel = .5f))
            SanityMeter(modifier = Modifier.size(96.dp),
                sanityUiState = PlayerSanityUiState(sanityLevel = .25f))
            SanityMeter(modifier = Modifier.size(96.dp),
                sanityUiState = PlayerSanityUiState(sanityLevel = 0f))
        }
    }
}
