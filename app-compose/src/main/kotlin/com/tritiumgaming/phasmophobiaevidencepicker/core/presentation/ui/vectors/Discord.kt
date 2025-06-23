package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors

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
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography

private val vector: ImageVector? = null

private fun getVector(groupColors: List<Color>): ImageVector =

        vector ?: Builder(
            name = "Discord",
            defaultWidth = 200.0.dp,
            defaultHeight = 152.0.dp,
            viewportWidth = 256.0f,
            viewportHeight = 194.01f
        ).apply {
            group {

                path(
                    fill = SolidColor(groupColors[0]), stroke = SolidColor(groupColors[0]),
                    strokeLineWidth = 17.78f, strokeLineCap = Butt, strokeLineJoin = Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero
                ) {
                    moveTo(221.41f, 144.49f)
                    curveToRelative(-14.41f, 10.65f, -30.55f, 18.75f, -47.7f, 23.92f)
                    curveToRelative(-3.86f, -5.18f, -7.28f, -10.69f, -10.21f, -16.45f)
                    curveToRelative(5.58f, -2.09f, 10.97f, -4.66f, 16.11f, -7.69f)
                    curveToRelative(-1.33f, -0.9f, -2.65f, -1.91f, -3.94f, -2.96f)
                    curveToRelative(-30.19f, 14.2f, -65.13f, 14.2f, -95.32f, 0.0f)
                    curveToRelative(-1.27f, 0.98f, -2.59f, 1.99f, -3.94f, 2.96f)
                    curveToRelative(5.12f, 3.03f, 10.5f, 5.59f, 16.08f, 7.68f)
                    curveToRelative(-2.94f, 5.77f, -6.35f, 11.27f, -10.21f, 16.47f)
                    curveToRelative(-17.14f, -5.19f, -33.27f, -13.29f, -47.67f, -23.93f)
                    curveToRelative(-3.33f, -34.99f, 3.33f, -70.49f, 27.93f, -106.88f)
                    curveToRelative(12.39f, -5.67f, 25.47f, -9.7f, 38.9f, -11.99f)
                    curveToRelative(1.84f, 3.28f, 3.5f, 6.66f, 4.98f, 10.12f)
                    curveToRelative(14.3f, -2.15f, 28.84f, -2.15f, 43.14f, 0.0f)
                    curveToRelative(1.48f, -3.46f, 3.14f, -6.84f, 4.98f, -10.12f)
                    curveToRelative(13.42f, 2.27f, 26.49f, 6.29f, 38.87f, 11.96f)
                    curveToRelative(21.33f, 31.18f, 31.92f, 66.35f, 28.01f, 106.93f)
                    close()
                }
                path(
                    fill = SolidColor(groupColors[1]), stroke = SolidColor(groupColors[1]),
                    strokeLineWidth = 1.48f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero
                ) {
                    moveTo(193.4f, 37.56f)
                    curveToRelative(-12.38f, -5.67f, -25.45f, -9.69f, -38.87f, -11.96f)
                    curveToRelative(-1.84f, 3.28f, -3.5f, 6.66f, -4.98f, 10.12f)
                    curveToRelative(-14.3f, -2.15f, -28.84f, -2.15f, -43.14f, 0.0f)
                    curveToRelative(-1.48f, -3.46f, -3.14f, -6.84f, -4.98f, -10.12f)
                    curveToRelative(-13.43f, 2.29f, -26.51f, 6.32f, -38.9f, 11.99f)
                    curveToRelative(-24.6f, 36.4f, -31.27f, 71.89f, -27.93f, 106.89f)
                    lineToRelative(0.0f, 0.0f)
                    curveToRelative(14.41f, 10.64f, 30.53f, 18.74f, 47.67f, 23.94f)
                    curveToRelative(3.86f, -5.19f, 7.28f, -10.7f, 10.21f, -16.47f)
                    curveToRelative(-5.57f, -2.08f, -10.96f, -4.65f, -16.08f, -7.68f)
                    curveToRelative(1.35f, -0.98f, 2.67f, -1.99f, 3.94f, -2.96f)
                    curveToRelative(30.19f, 14.2f, 65.13f, 14.2f, 95.32f, 0.0f)
                    curveToRelative(1.29f, 1.05f, 2.61f, 2.06f, 3.94f, 2.96f)
                    curveToRelative(-5.13f, 3.03f, -10.52f, 5.6f, -16.11f, 7.69f)
                    curveToRelative(2.93f, 5.76f, 6.35f, 11.27f, 10.21f, 16.45f)
                    curveToRelative(17.16f, -5.17f, 33.29f, -13.27f, 47.7f, -23.92f)
                    lineToRelative(0.0f, 0.0f)
                    curveToRelative(3.91f, -40.58f, -6.68f, -75.75f, -28.01f, -106.93f)
                    close()
                    moveTo(96.7f, 122.96f)
                    curveToRelative(-9.29f, 0.0f, -16.97f, -8.43f, -16.97f, -18.81f)
                    curveToRelative(0.0f, -10.37f, 7.41f, -18.88f, 16.94f, -18.88f)
                    curveToRelative(9.53f, 0.0f, 17.15f, 8.51f, 16.98f, 18.88f)
                    curveToRelative(-0.16f, 10.37f, -7.48f, 18.81f, -16.95f, 18.81f)
                    close()
                    moveTo(159.3f, 122.96f)
                    curveToRelative(-9.31f, 0.0f, -16.95f, -8.43f, -16.95f, -18.81f)
                    curveToRelative(0.0f, -10.37f, 7.41f, -18.88f, 16.95f, -18.88f)
                    curveToRelative(9.54f, 0.0f, 17.1f, 8.51f, 16.94f, 18.88f)
                    curveToRelative(-0.16f, 10.37f, -7.47f, 18.81f, -16.94f, 18.81f)
                    close()
                }
            }
        }.build()

@Composable
fun Discord(
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
            Discord()
        }
    }
}
