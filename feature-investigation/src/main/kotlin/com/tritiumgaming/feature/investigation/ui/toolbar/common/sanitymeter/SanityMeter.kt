package com.tritiumgaming.feature.investigation.ui.toolbar.common.sanitymeter

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.util.ColorUtils
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel
import kotlin.math.min

@Composable
fun SanityMeter(
    modifier: Modifier = Modifier,
    sanityUiState: PlayerSanityUiState
) {
    val sanityPercent = sanityUiState.sanityLevel

    val sanityPercentString = "$sanityPercent"
        .substring(0, min(4, sanityPercent.toString().length))

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

            Text(
                modifier = Modifier
                    .fillMaxSize(.75f)
                    .align(Alignment.Center),
                text = sanityPercentString,
                style = LocalTypography.current.quaternary.regular.copy(
                    shadow = Shadow(
                        color = LocalPalette.current.scrim,
                        blurRadius = 2f
                    )
                ),
                color = LocalPalette.current.primary,
                fontSize = 18.sp,
            )

        }

    }
}

@Composable
fun SanityMeter(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationScreenViewModel
) {
    val sanityLevel by investigationViewModel.playerSanityUiState.collectAsStateWithLifecycle()

    val sanityPercent = sanityLevel.sanityLevel
    /*val sanityPercentString = "$sanityPercent"
        .substring(0, min(4, sanityPercent.toString().length))*/
    val sanityPercentString = "$sanityPercent"

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

            Text(
                modifier = Modifier
                    .fillMaxSize(.75f)
                    .align(Alignment.Center),
                text = sanityPercentString,
                style = LocalTypography.current.quaternary.regular.copy(
                    shadow = Shadow(
                        color = LocalPalette.current.scrim,
                        blurRadius = 2f
                    )
                ),
                color = LocalPalette.current.primary,
                fontSize = 18.sp,
            )

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
private fun PreviewSanityPie(
    modifier: Modifier,
    sanityPercent: Float = 0f
) {

    SelectiveTheme {
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

                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "$sanityPercent".substring(0, min(4, sanityPercent.toString().length)),
                    style = LocalTypography.current.quaternary.bold.copy(
                        shadow = Shadow(
                            color = LocalPalette.current.scrim,
                            blurRadius = 8f
                        )
                    ),
                    color = LocalPalette.current.primary,
                    fontSize = 18.sp,
                )
            }

        }
    }
}

@Composable
@Preview
private fun PreviewSanityPieEmpty() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PreviewSanityPie(modifier = Modifier.size(96.dp), sanityPercent = 1f)
        PreviewSanityPie(modifier = Modifier.size(96.dp), sanityPercent = .75f)
        PreviewSanityPie(modifier = Modifier.size(96.dp), sanityPercent = .5f)
        PreviewSanityPie(modifier = Modifier.size(96.dp), sanityPercent = .25f)
        PreviewSanityPie(modifier = Modifier.size(96.dp), sanityPercent = 0f)
    }
}
