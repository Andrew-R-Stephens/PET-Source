package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

fun getSimpleFlashlightVector(groupColors: List<Color>): ImageVector =
    Builder(
        name = "IconShFlashlight",
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
            moveTo(215.2f, 5.3f)
            curveToRelative(-1.1f, 1.2f, -3.7f, 5.8f, -5.7f, 10.2f)
            curveToRelative(-2.0f, 4.4f, -4.6f, 10.0f, -5.7f, 12.4f)
            curveToRelative(-2.2f, 4.8f, -1.9f, 7.6f, 1.2f, 10.9f)
            curveToRelative(1.5f, 1.6f, 3.2f, 2.2f, 6.5f, 2.2f)
            curveToRelative(5.4f, -0.0f, 7.0f, -1.5f, 11.2f, -10.7f)
            curveToRelative(7.4f, -16.2f, 8.1f, -19.9f, 4.7f, -24.7f)
            curveToRelative(-2.6f, -3.5f, -9.1f, -3.6f, -12.2f, -0.3f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(161.5f, 28.7f)
            curveToRelative(-1.1f, 0.3f, -3.3f, 1.4f, -5.0f, 2.6f)
            curveToRelative(-4.6f, 3.1f, -23.4f, 21.8f, -25.1f, 24.9f)
            curveToRelative(-2.1f, 4.1f, -1.8f, 11.4f, 0.7f, 15.0f)
            lineToRelative(2.1f, 3.0f)
            lineToRelative(-3.3f, 3.6f)
            curveToRelative(-1.9f, 2.0f, -6.3f, 6.8f, -10.0f, 10.7f)
            curveToRelative(-9.7f, 10.3f, -11.5f, 13.9f, -16.4f, 33.5f)
            lineToRelative(-1.6f, 6.5f)
            lineToRelative(-36.2f, 36.5f)
            curveToRelative(-19.9f, 20.1f, -41.0f, 41.2f, -47.0f, 46.9f)
            curveToRelative(-5.9f, 5.7f, -11.6f, 11.8f, -12.7f, 13.6f)
            curveToRelative(-2.8f, 4.5f, -2.8f, 16.6f, 0.0f, 21.2f)
            curveToRelative(2.1f, 3.6f, 20.8f, 22.3f, 22.1f, 22.3f)
            curveToRelative(0.5f, -0.0f, 0.9f, 0.4f, 0.9f, 1.0f)
            curveToRelative(0.0f, 1.7f, 22.4f, 23.2f, 25.7f, 24.6f)
            curveToRelative(3.6f, 1.6f, 15.0f, 1.9f, 17.2f, 0.5f)
            curveToRelative(1.8f, -1.2f, 46.1f, -44.3f, 51.0f, -49.6f)
            curveToRelative(5.0f, -5.6f, 34.4f, -34.8f, 41.7f, -41.5f)
            curveToRelative(5.1f, -4.8f, 7.1f, -5.9f, 13.9f, -7.8f)
            curveToRelative(19.8f, -5.6f, 26.9f, -9.8f, 39.9f, -23.0f)
            lineToRelative(6.9f, -7.0f)
            lineToRelative(3.1f, 1.9f)
            curveToRelative(1.7f, 1.0f, 4.6f, 1.9f, 6.4f, 1.9f)
            curveToRelative(6.6f, -0.0f, 8.3f, -1.3f, 31.4f, -23.7f)
            curveToRelative(4.8f, -4.7f, 6.5f, -10.8f, 4.3f, -16.0f)
            curveToRelative(-1.9f, -4.5f, -95.3f, -98.5f, -100.2f, -100.8f)
            curveToRelative(-3.1f, -1.5f, -6.0f, -1.7f, -9.8f, -0.8f)
            close()
            moveTo(174.2f, 87.4f)
            curveToRelative(0.7f, 0.8f, 2.0f, 2.2f, 2.9f, 3.2f)
            curveToRelative(0.9f, 1.1f, 9.3f, 9.5f, 18.8f, 18.8f)
            curveToRelative(18.1f, 17.9f, 18.6f, 18.8f, 15.6f, 24.6f)
            curveToRelative(-3.5f, 6.8f, -10.4f, 8.0f, -15.4f, 2.7f)
            curveToRelative(-1.7f, -1.7f, -9.4f, -10.0f, -17.0f, -18.2f)
            curveToRelative(-7.7f, -8.3f, -14.9f, -15.8f, -16.0f, -16.7f)
            curveToRelative(-2.5f, -2.1f, -3.5f, -5.9f, -2.7f, -9.3f)
            curveToRelative(1.2f, -4.9f, 10.7f, -8.4f, 13.8f, -5.1f)
            close()
            moveTo(145.6f, 156.4f)
            curveToRelative(13.9f, 13.3f, 15.0f, 14.7f, 15.2f, 18.2f)
            curveToRelative(0.1f, 2.2f, -0.1f, 4.4f, -0.6f, 5.0f)
            curveToRelative(-1.6f, 2.3f, -5.9f, 4.4f, -9.1f, 4.4f)
            curveToRelative(-3.0f, -0.0f, -4.9f, -1.5f, -17.3f, -13.8f)
            curveToRelative(-7.6f, -7.5f, -14.1f, -14.7f, -14.4f, -16.0f)
            curveToRelative(-1.4f, -5.4f, 3.2f, -12.2f, 8.3f, -12.2f)
            curveToRelative(2.3f, -0.0f, 5.8f, 2.8f, 17.9f, 14.4f)
            close()
            moveTo(115.5f, 185.6f)
            curveToRelative(1.7f, 1.9f, 2.5f, 3.8f, 2.5f, 6.4f)
            curveToRelative(0.0f, 3.4f, -0.8f, 4.5f, -8.4f, 11.8f)
            curveToRelative(-4.7f, 4.5f, -9.7f, 8.4f, -11.1f, 8.8f)
            curveToRelative(-1.6f, 0.4f, -4.1f, -0.0f, -6.5f, -1.1f)
            curveToRelative(-3.1f, -1.4f, -4.1f, -2.5f, -4.5f, -4.9f)
            curveToRelative(-0.9f, -4.8f, 0.6f, -7.5f, 9.3f, -15.9f)
            curveToRelative(7.3f, -7.1f, 8.3f, -7.7f, 12.1f, -7.7f)
            curveToRelative(3.2f, -0.0f, 4.8f, 0.6f, 6.6f, 2.6f)
            close()
            moveTo(64.7f, 218.2f)
            curveToRelative(10.4f, 9.9f, 22.2f, 23.5f, 22.9f, 26.3f)
            curveToRelative(1.0f, 4.0f, -0.4f, 7.4f, -3.9f, 9.5f)
            curveToRelative(-5.8f, 3.6f, -4.9f, 4.2f, -24.4f, -15.3f)
            curveToRelative(-14.3f, -14.1f, -15.4f, -16.1f, -11.7f, -21.7f)
            curveToRelative(4.8f, -7.2f, 8.6f, -6.9f, 17.1f, 1.2f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(250.5f, 37.0f)
            curveToRelative(-1.1f, 0.4f, -6.2f, 5.1f, -11.2f, 10.3f)
            curveToRelative(-8.6f, 8.7f, -9.3f, 9.7f, -9.3f, 13.5f)
            curveToRelative(0.0f, 3.3f, 0.6f, 4.5f, 3.1f, 6.6f)
            curveToRelative(5.7f, 4.9f, 8.0f, 3.9f, 20.1f, -8.2f)
            curveToRelative(9.3f, -9.3f, 10.8f, -11.2f, 10.8f, -14.1f)
            curveToRelative(0.0f, -6.4f, -7.2f, -10.8f, -13.5f, -8.1f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(273.5f, 76.1f)
            curveToRelative(-6.6f, 3.2f, -12.8f, 6.8f, -13.7f, 8.0f)
            curveToRelative(-4.0f, 4.9f, -0.9f, 11.6f, 6.0f, 12.9f)
            curveToRelative(3.5f, 0.6f, 4.8f, 0.3f, 8.9f, -2.1f)
            curveToRelative(2.6f, -1.6f, 5.6f, -2.9f, 6.5f, -2.9f)
            curveToRelative(2.1f, -0.0f, 8.2f, -2.7f, 12.1f, -5.3f)
            curveToRelative(2.4f, -1.6f, 2.7f, -2.5f, 2.7f, -7.2f)
            curveToRelative(0.0f, -4.6f, -0.4f, -5.6f, -2.6f, -7.4f)
            curveToRelative(-1.5f, -1.2f, -3.8f, -2.1f, -5.3f, -2.0f)
            curveToRelative(-1.4f, -0.0f, -8.0f, 2.7f, -14.6f, 6.0f)
            close()
        }
    }
        .build()