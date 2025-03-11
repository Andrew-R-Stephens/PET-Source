package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils

@Composable
fun SanityMeterView(
    investigationViewModel: InvestigationViewModel,
    modifier: Modifier = Modifier
) {
    val sanityLevel = investigationViewModel.sanityModel?.sanityLevel?.collectAsState()
    val sanityPercent = (sanityLevel?.value ?: 1f) * .01f

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .size(12.dp)
            .border(
                1.dp,
                Color(
                    ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityBorderColor)
                ),
                CircleShape
            )
    ) {
        SanityPie(
            startColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityPieStartColor),
            endColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityPieEndColor),
            interpolation = sanityPercent
        )

        Box(
            modifier = Modifier
                .fillMaxSize(.8f)
                .align(Alignment.Center)
        ) {

            SanityImageLayer(
                image = R.drawable.icon_sanityhead_skull,
                startColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityHeadSkullColor),
                endColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityHeadSkullColor),
                interpolation = sanityPercent
            )
            SanityImageLayer(
                image = R.drawable.icon_sanityhead_brain,
                startColor = (Color.Gray).toArgb(),
                endColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityPieEndColor),
                interpolation = sanityPercent
            )
            SanityImageLayer(
                image = R.drawable.icon_sanityhead_border,
                startColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityBorderColor),
                endColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityBorderColor),
                interpolation = sanityPercent
            )
        }

    }
}

@Composable
fun SanityImageLayer(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int = R.drawable.icon_sanityhead_brain,
    @ColorInt startColor: Int = (Color.Yellow).toArgb(),
    @ColorInt endColor: Int = (Color.Red).toArgb(),
    interpolation: Float = 1f
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "",
        colorFilter = ColorFilter.tint(
            Color(
                ColorUtils.interpolate(
                    startColor,
                    endColor,
                    interpolation
                )
            ),
            BlendMode.SrcIn),
        modifier = modifier
            .fillMaxSize()
    )
}

@Composable
fun SanityPie(
    modifier: Modifier = Modifier,
    @ColorInt startColor: Int = colorResource(R.color.white).toArgb(),
    @ColorInt endColor: Int = colorResource(R.color.red).toArgb(),
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