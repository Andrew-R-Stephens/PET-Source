package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.graphics.Color.toArgb
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toAndroidColorSpace
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorLong
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityData
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel

@Composable
@Preview
fun SanityMeterView(
    evidenceViewModel: EvidenceViewModel = EvidenceViewModel(),
    modifier: Modifier = Modifier
) {
    val insanityPercent = evidenceViewModel.sanityData?.insanityPercent?.collectAsState()

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
            interpolation = insanityPercent?.value ?: 0f
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
                interpolation = insanityPercent?.value ?: 0f
            )
            SanityImageLayer(
                image = R.drawable.icon_sanityhead_brain,
                startColor = (Color.Gray).toArgb(),
                endColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityPieEndColor),
                interpolation = insanityPercent?.value ?: 0f
            )
            SanityImageLayer(
                image = R.drawable.icon_sanityhead_border,
                startColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityBorderColor),
                endColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.sanityBorderColor),
                interpolation = insanityPercent?.value ?: 0f
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
    interpolation: Float = .6f
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
    @ColorInt startColor: Int = R.color.white,
    @ColorInt endColor: Int = Color(R.color.red).toArgb(),
    interpolation: Float = .9f
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

fun setSanityMeterView(
    composeView: ComposeView?,
    evidenceViewModel: EvidenceViewModel = EvidenceViewModel()
) {
    composeView?.setContent {
        SanityMeterView(
            evidenceViewModel = evidenceViewModel
        )
    }
}