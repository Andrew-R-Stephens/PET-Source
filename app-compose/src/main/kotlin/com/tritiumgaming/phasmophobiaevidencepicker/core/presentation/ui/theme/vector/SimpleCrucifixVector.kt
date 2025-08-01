package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.vector

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

fun getSimpleCrucifixVector(groupColors: List<Color>): ImageVector =
    Builder(
        name = "IconShCrucifix",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 300.0f,
        viewportHeight = 300.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(143.9f, 3.9f)
            curveToRelative(-4.2f, 1.4f, -7.3f, 3.8f, -9.2f, 7.4f)
            curveToRelative(-1.3f, 2.3f, -3.1f, 3.8f, -6.0f, 4.8f)
            curveToRelative(-2.4f, 0.9f, -5.4f, 3.1f, -7.2f, 5.3f)
            curveToRelative(-2.6f, 3.3f, -3.0f, 4.5f, -3.0f, 10.1f)
            curveToRelative(0.0f, 7.4f, 1.9f, 10.8f, 7.8f, 14.0f)
            lineToRelative(3.7f, 2.0f)
            lineToRelative(0.0f, 23.7f)
            lineToRelative(0.0f, 23.8f)
            lineToRelative(-25.7f, -0.0f)
            lineToRelative(-25.8f, -0.1f)
            lineToRelative(-2.0f, -3.2f)
            curveToRelative(-1.1f, -1.7f, -3.5f, -4.2f, -5.4f, -5.4f)
            curveToRelative(-8.1f, -5.5f, -20.6f, -0.8f, -23.1f, 8.8f)
            curveToRelative(-0.7f, 2.3f, -1.8f, 4.0f, -3.1f, 4.4f)
            curveToRelative(-2.4f, 0.8f, -6.2f, 4.3f, -7.7f, 7.1f)
            curveToRelative(-1.8f, 3.4f, -2.1f, 10.6f, -0.7f, 13.9f)
            curveToRelative(1.6f, 3.8f, 5.8f, 8.1f, 8.5f, 9.0f)
            curveToRelative(1.3f, 0.4f, 2.3f, 1.8f, 2.7f, 3.7f)
            curveToRelative(0.9f, 4.4f, 7.0f, 10.4f, 12.0f, 11.7f)
            curveToRelative(3.4f, 0.9f, 4.9f, 0.8f, 8.2f, -0.5f)
            curveToRelative(4.3f, -1.7f, 10.1f, -7.4f, 10.1f, -9.9f)
            curveToRelative(0.0f, -1.3f, 3.8f, -1.5f, 26.0f, -1.5f)
            lineToRelative(26.0f, -0.0f)
            lineToRelative(0.0f, 60.5f)
            lineToRelative(0.0f, 60.5f)
            lineToRelative(-3.4f, 1.4f)
            curveToRelative(-5.3f, 2.2f, -7.9f, 6.8f, -8.0f, 14.2f)
            curveToRelative(-0.1f, 7.8f, 1.8f, 10.8f, 9.4f, 14.6f)
            curveToRelative(2.9f, 1.5f, 6.1f, 4.1f, 7.0f, 5.6f)
            curveToRelative(5.7f, 9.3f, 17.9f, 10.6f, 24.9f, 2.7f)
            curveToRelative(1.7f, -2.0f, 3.1f, -4.2f, 3.1f, -5.0f)
            curveToRelative(0.0f, -0.8f, 0.7f, -1.5f, 1.5f, -1.5f)
            curveToRelative(3.1f, -0.0f, 9.6f, -4.2f, 11.5f, -7.3f)
            curveToRelative(1.0f, -1.8f, 2.4f, -3.7f, 2.9f, -4.4f)
            curveToRelative(1.4f, -1.6f, 1.4f, -8.0f, 0.0f, -9.6f)
            curveToRelative(-0.5f, -0.7f, -1.9f, -2.7f, -3.0f, -4.5f)
            curveToRelative(-1.1f, -1.8f, -3.5f, -4.0f, -5.4f, -4.8f)
            lineToRelative(-3.5f, -1.4f)
            lineToRelative(0.0f, -60.5f)
            lineToRelative(0.0f, -60.5f)
            lineToRelative(25.9f, -0.0f)
            lineToRelative(26.0f, -0.0f)
            lineToRelative(0.6f, 2.2f)
            curveToRelative(1.7f, 5.6f, 8.3f, 9.8f, 15.3f, 9.8f)
            curveToRelative(5.6f, -0.0f, 10.2f, -3.4f, 13.3f, -9.9f)
            curveToRelative(1.8f, -3.7f, 3.6f, -5.8f, 5.6f, -6.6f)
            curveToRelative(1.6f, -0.7f, 4.1f, -2.7f, 5.6f, -4.4f)
            curveToRelative(2.3f, -2.8f, 2.7f, -4.2f, 2.7f, -9.4f)
            curveToRelative(0.0f, -7.6f, -2.6f, -11.9f, -8.7f, -14.4f)
            curveToRelative(-3.0f, -1.3f, -4.3f, -2.4f, -4.3f, -3.8f)
            curveToRelative(0.0f, -3.0f, -3.6f, -8.3f, -7.2f, -10.6f)
            curveToRelative(-2.6f, -1.5f, -4.6f, -1.9f, -8.8f, -1.7f)
            curveToRelative(-6.3f, 0.4f, -10.0f, 2.6f, -12.7f, 7.5f)
            lineToRelative(-1.8f, 3.3f)
            lineToRelative(-25.7f, -0.0f)
            lineToRelative(-25.8f, -0.0f)
            lineToRelative(0.0f, -24.1f)
            lineToRelative(0.0f, -24.0f)
            lineToRelative(2.8f, -1.0f)
            curveToRelative(3.4f, -1.2f, 7.0f, -5.7f, 7.8f, -9.9f)
            curveToRelative(0.4f, -1.6f, 1.0f, -3.0f, 1.5f, -3.0f)
            curveToRelative(1.1f, -0.0f, 1.2f, -4.7f, 0.2f, -5.2f)
            curveToRelative(-0.5f, -0.2f, -1.6f, -2.0f, -2.5f, -4.1f)
            curveToRelative(-1.8f, -3.8f, -6.4f, -7.4f, -10.9f, -8.3f)
            curveToRelative(-1.6f, -0.4f, -2.9f, -1.6f, -3.6f, -3.3f)
            curveToRelative(-2.2f, -5.9f, -11.9f, -10.2f, -18.4f, -8.2f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(116.2f, 54.1f)
            curveToRelative(-7.9f, 4.5f, -16.6f, 11.4f, -21.1f, 16.7f)
            curveToRelative(-4.5f, 5.2f, -11.4f, 17.5f, -10.4f, 18.6f)
            curveToRelative(0.3f, 0.3f, 3.4f, 0.6f, 6.9f, 0.6f)
            curveToRelative(6.1f, -0.0f, 6.3f, -0.1f, 8.5f, -3.7f)
            curveToRelative(3.2f, -5.2f, 14.0f, -15.7f, 19.4f, -18.8f)
            lineToRelative(4.5f, -2.7f)
            lineToRelative(0.0f, -6.9f)
            curveToRelative(0.0f, -4.9f, -0.4f, -6.9f, -1.2f, -6.9f)
            curveToRelative(-0.7f, -0.0f, -3.7f, 1.4f, -6.6f, 3.1f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(172.6f, 54.5f)
            curveToRelative(-0.4f, 1.9f, -0.4f, 5.1f, 0.0f, 7.0f)
            curveToRelative(0.5f, 3.0f, 1.4f, 4.0f, 5.3f, 6.0f)
            curveToRelative(5.8f, 2.9f, 12.9f, 9.7f, 17.7f, 16.9f)
            lineToRelative(3.7f, 5.6f)
            lineToRelative(6.3f, -0.0f)
            curveToRelative(7.7f, -0.0f, 7.8f, -0.5f, 3.2f, -9.4f)
            curveToRelative(-4.3f, -8.1f, -18.4f, -22.2f, -25.8f, -25.8f)
            curveToRelative(-9.1f, -4.4f, -9.7f, -4.4f, -10.4f, -0.3f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(85.2f, 142.2f)
            curveToRelative(2.9f, 8.2f, 11.1f, 18.7f, 19.3f, 24.8f)
            curveToRelative(6.6f, 4.9f, 14.4f, 9.6f, 17.3f, 10.5f)
            curveToRelative(2.1f, 0.6f, 2.2f, 0.4f, 2.2f, -6.2f)
            lineToRelative(0.0f, -6.9f)
            lineToRelative(-4.4f, -2.8f)
            curveToRelative(-5.5f, -3.4f, -16.7f, -14.4f, -19.6f, -19.1f)
            curveToRelative(-2.1f, -3.4f, -2.3f, -3.5f, -9.0f, -3.5f)
            lineToRelative(-6.9f, -0.0f)
            lineToRelative(1.1f, 3.2f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(195.7f, 144.2f)
            curveToRelative(-4.5f, 7.0f, -12.2f, 14.5f, -17.5f, 17.0f)
            curveToRelative(-5.5f, 2.7f, -6.6f, 4.7f, -5.7f, 11.5f)
            curveToRelative(0.6f, 4.9f, 0.8f, 5.3f, 2.8f, 4.7f)
            curveToRelative(12.0f, -3.5f, 27.8f, -18.0f, 34.8f, -31.6f)
            curveToRelative(3.2f, -6.2f, 2.8f, -6.8f, -4.6f, -6.8f)
            lineToRelative(-6.4f, -0.0f)
            lineToRelative(-3.4f, 5.2f)
            close()
        }
    }
        .build()