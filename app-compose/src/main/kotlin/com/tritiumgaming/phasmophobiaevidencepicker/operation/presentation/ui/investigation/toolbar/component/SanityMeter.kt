package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.ColorUtils
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel.InvestigationViewModel

@Composable
fun SanityMeterView(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {
    val sanityLevel = investigationViewModel.sanityLevel.collectAsStateWithLifecycle()
    val sanityPercent = sanityLevel.value * .01f

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .size(12.dp)
            .border(
                1.dp,
                LocalPalette.current.sanityBorderColor,
                CircleShape
            )
    ) {
        SanityPie(
            startColor = LocalPalette.current.sanityPieStartColor.toArgb(),
            endColor = LocalPalette.current.sanityPieEndColor.toArgb(),
            interpolation = sanityPercent
        )

        Box(
            modifier = Modifier
                .fillMaxSize(.8f)
                .align(Alignment.Center)
        ) {

            SanityImageLayer(
                image = R.drawable.icon_sanityhead_skull,
                startColor = LocalPalette.current.sanityHeadSkullColor.toArgb(),
                endColor = LocalPalette.current.sanityHeadSkullColor.toArgb(),
                interpolation = sanityPercent
            )
            SanityImageLayer(
                image = R.drawable.icon_sanityhead_brain,
                startColor = (Color.Gray).toArgb(),
                endColor = LocalPalette.current.sanityPieEndColor.toArgb(),
                interpolation = sanityPercent
            )
            SanityImageLayer(
                image = R.drawable.icon_sanityhead_border,
                startColor = LocalPalette.current.sanityBorderColor.toArgb(),
                endColor = LocalPalette.current.sanityBorderColor.toArgb(),
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
    @ColorInt startColor: Int = Color.White.toArgb(),
    @ColorInt endColor: Int = Color.Red.toArgb(),
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