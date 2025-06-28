package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography

private val vector: ImageVector? = null

private fun getVector(groupColors: List<Color>): ImageVector =

    vector ?: Builder(
        name = "ButtonScratched",
        defaultWidth = 200.0.dp,
        defaultHeight = 50.0.dp,
        viewportWidth = 720.0f,
        viewportHeight = 180.0f
    ).apply {
        path(
            fill = SolidColor(groupColors[0]), stroke = SolidColor(groupColors[0]),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(605.0f, 11.5f)
            curveToRelative(-1.1f, 1.3f, -21.5f, 1.5f, -177.0f, 1.5f)
            curveToRelative(-155.0f, -0.0f, -175.9f, 0.2f, -177.0f, 1.5f)
            curveToRelative(-1.1f, 1.3f, -13.3f, 1.5f, -99.0f, 1.5f)
            curveToRelative(-85.7f, -0.0f, -97.9f, -0.2f, -99.0f, -1.5f)
            curveToRelative(-1.6f, -1.9f, -4.4f, -1.9f, -6.0f, -0.0f)
            curveToRelative(-1.0f, 1.2f, -4.7f, 1.5f, -18.4f, 1.7f)
            curveToRelative(-16.7f, 0.3f, -17.1f, 0.3f, -17.4f, 2.5f)
            curveToRelative(-0.2f, 1.2f, -1.0f, 2.7f, -1.8f, 3.3f)
            curveToRelative(-2.1f, 1.8f, -2.1f, 34.2f, 0.1f, 36.0f)
            curveToRelative(1.3f, 1.1f, 1.5f, 8.4f, 1.7f, 51.4f)
            lineToRelative(0.3f, 50.1f)
            lineToRelative(17.1f, 0.3f)
            curveToRelative(13.7f, 0.2f, 17.4f, 0.5f, 18.4f, 1.7f)
            curveToRelative(1.1f, 1.3f, 37.7f, 1.5f, 300.0f, 1.5f)
            curveToRelative(265.7f, -0.0f, 298.9f, 0.2f, 300.0f, 1.5f)
            curveToRelative(1.1f, 1.3f, 5.2f, 1.5f, 28.9f, 1.3f)
            lineToRelative(27.6f, -0.3f)
            lineToRelative(0.0f, -77.5f)
            lineToRelative(0.0f, -77.5f)
            lineToRelative(-48.6f, -0.3f)
            curveToRelative(-42.4f, -0.2f, -48.8f, -0.0f, -49.9f, 1.3f)
            close()
        }
        path(
            fill = SolidColor(groupColors[1]), stroke = SolidColor(groupColors[1]),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(605.0f, 11.5f)
            curveToRelative(0.0f, 1.3f, -20.3f, 1.5f, -177.0f, 1.5f)
            curveToRelative(-156.7f, -0.0f, -177.0f, 0.2f, -177.0f, 1.5f)
            curveToRelative(0.0f, 1.3f, -11.7f, 1.5f, -99.0f, 1.5f)
            curveToRelative(-87.3f, -0.0f, -99.0f, -0.2f, -99.0f, -1.5f)
            curveToRelative(0.0f, -1.0f, -1.0f, -1.5f, -3.0f, -1.5f)
            curveToRelative(-2.0f, -0.0f, -3.0f, 0.5f, -3.0f, 1.5f)
            curveToRelative(0.0f, 1.3f, -2.7f, 1.5f, -18.0f, 1.5f)
            lineToRelative(-18.0f, -0.0f)
            lineToRelative(0.0f, 3.0f)
            curveToRelative(0.0f, 2.0f, -0.5f, 3.0f, -1.5f, 3.0f)
            curveToRelative(-1.3f, -0.0f, -1.5f, 2.7f, -1.5f, 18.0f)
            curveToRelative(0.0f, 15.3f, 0.2f, 18.0f, 1.5f, 18.0f)
            curveToRelative(1.3f, -0.0f, 1.5f, 6.3f, 1.5f, 51.0f)
            lineToRelative(0.0f, 51.0f)
            lineToRelative(18.0f, -0.0f)
            curveToRelative(15.3f, -0.0f, 18.0f, 0.2f, 18.0f, 1.5f)
            curveToRelative(0.0f, 1.3f, 34.0f, 1.5f, 300.0f, 1.5f)
            curveToRelative(266.0f, -0.0f, 300.0f, 0.2f, 300.0f, 1.5f)
            curveToRelative(0.0f, 1.3f, 3.8f, 1.5f, 28.5f, 1.5f)
            lineToRelative(28.5f, -0.0f)
            lineToRelative(0.0f, -78.0f)
            lineToRelative(0.0f, -78.0f)
            lineToRelative(-49.5f, -0.0f)
            curveToRelative(-43.3f, -0.0f, -49.5f, 0.2f, -49.5f, 1.5f)
            close()
            moveTo(687.1f, 23.5f)
            curveToRelative(1.6f, 0.9f, 3.4f, 2.7f, 3.9f, 4.1f)
            curveToRelative(0.6f, 1.5f, 1.0f, 26.0f, 1.0f, 60.4f)
            curveToRelative(0.0f, 34.4f, -0.4f, 58.9f, -1.0f, 60.4f)
            curveToRelative(-1.7f, 4.5f, -5.6f, 5.6f, -19.2f, 5.6f)
            curveToRelative(-6.8f, -0.0f, -16.6f, -0.7f, -21.8f, -1.5f)
            curveToRelative(-8.2f, -1.3f, -50.1f, -1.5f, -300.0f, -1.5f)
            curveToRelative(-249.9f, -0.0f, -291.8f, -0.2f, -300.0f, -1.5f)
            curveToRelative(-5.2f, -0.8f, -11.8f, -1.5f, -14.7f, -1.5f)
            curveToRelative(-6.3f, -0.0f, -9.7f, -1.4f, -11.2f, -4.7f)
            curveToRelative(-0.7f, -1.6f, -1.1f, -14.6f, -1.1f, -40.1f)
            curveToRelative(0.0f, -30.4f, -0.3f, -38.9f, -1.5f, -43.5f)
            curveToRelative(-1.8f, -7.1f, -2.0f, -24.2f, -0.2f, -27.6f)
            curveToRelative(0.6f, -1.4f, 2.6f, -2.8f, 4.2f, -3.3f)
            curveToRelative(1.7f, -0.5f, 52.6f, -0.8f, 113.1f, -0.8f)
            curveToRelative(87.3f, -0.0f, 110.7f, -0.2f, 113.0f, -1.2f)
            curveToRelative(2.3f, -1.0f, 39.2f, -1.4f, 178.4f, -1.9f)
            curveToRelative(111.9f, -0.4f, 177.3f, -1.0f, 180.5f, -1.6f)
            curveToRelative(2.8f, -0.6f, 20.4f, -1.1f, 39.3f, -1.2f)
            curveToRelative(29.2f, -0.1f, 34.7f, 0.1f, 37.3f, 1.4f)
            close()
        }
    }
        .build()

@Composable
fun ButtonScratched(
    modifier: Modifier = Modifier
) {

    Image(
        modifier = modifier,
        imageVector = getVector(
            listOf(
                LocalPalette.current.background.color,
                LocalPalette.current.textFamily.body
            )
        ),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}

@Preview
@Composable
private fun Preview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            ButtonScratched()
        }
    }
}
