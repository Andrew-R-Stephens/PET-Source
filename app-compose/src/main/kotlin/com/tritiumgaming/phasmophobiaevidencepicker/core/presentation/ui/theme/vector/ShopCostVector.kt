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

fun getShopCostVector(groupColors: List<Color>): ImageVector =
    Builder(
        name = "ShopCostVector",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 300.0f,
        viewportHeight = 300.0f
    ).apply {
        path(
            fill = SolidColor(groupColors[0]), stroke = SolidColor(groupColors[1]),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(165.0f, 49.5f)
            curveToRelative(-0.8f, 0.3f, -6.4f, 0.9f, -12.5f, 1.5f)
            curveToRelative(-16.2f, 1.4f, -27.3f, 3.0f, -33.5f, 4.7f)
            curveToRelative(-3.0f, 0.8f, -7.3f, 1.9f, -9.5f, 2.4f)
            curveToRelative(-5.2f, 1.2f, -13.9f, 4.7f, -19.2f, 7.7f)
            curveToRelative(-4.8f, 2.6f, -8.3f, 6.9f, -8.3f, 10.1f)
            curveToRelative(0.0f, 1.9f, 0.6f, 2.1f, 6.0f, 2.1f)
            curveToRelative(12.7f, -0.0f, 36.1f, 8.3f, 46.8f, 16.7f)
            curveToRelative(1.3f, 1.0f, 3.6f, 2.7f, 5.0f, 3.9f)
            curveToRelative(3.8f, 3.0f, 15.7f, 3.9f, 51.2f, 3.9f)
            curveToRelative(27.3f, -0.0f, 40.9f, -0.8f, 55.5f, -3.4f)
            curveToRelative(2.8f, -0.5f, 7.9f, -1.4f, 11.5f, -2.0f)
            curveToRelative(9.6f, -1.7f, 22.6f, -5.8f, 29.8f, -9.3f)
            curveToRelative(6.9f, -3.3f, 11.2f, -8.0f, 11.2f, -12.1f)
            curveToRelative(0.0f, -5.4f, -9.7f, -12.2f, -23.2f, -16.2f)
            curveToRelative(-14.5f, -4.4f, -24.3f, -6.3f, -45.3f, -8.6f)
            curveToRelative(-8.4f, -1.0f, -62.9f, -2.1f, -65.5f, -1.4f)
            close()
        }
        path(
            fill = SolidColor(groupColors[0]), stroke = SolidColor(groupColors[1]),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(66.5f, 90.9f)
            curveToRelative(-30.1f, 5.7f, -56.4f, 30.0f, -63.3f, 58.5f)
            curveToRelative(-2.3f, 9.5f, -2.7f, 30.2f, -0.7f, 38.4f)
            curveToRelative(3.6f, 15.0f, 10.3f, 27.2f, 21.1f, 38.5f)
            curveToRelative(24.7f, 25.5f, 61.4f, 31.8f, 93.9f, 16.1f)
            curveToRelative(8.4f, -4.0f, 10.5f, -5.6f, 20.5f, -15.4f)
            curveToRelative(8.9f, -8.8f, 13.3f, -15.3f, 18.8f, -28.0f)
            curveToRelative(7.6f, -17.6f, 6.5f, -44.7f, -2.5f, -63.3f)
            curveToRelative(-9.1f, -18.7f, -24.2f, -32.8f, -43.3f, -40.5f)
            curveToRelative(-6.1f, -2.4f, -9.0f, -3.2f, -17.5f, -4.7f)
            curveToRelative(-10.9f, -1.8f, -15.3f, -1.8f, -27.0f, 0.4f)
            close()
            moveTo(86.0f, 123.4f)
            lineToRelative(0.0f, 5.5f)
            lineToRelative(4.8f, 0.6f)
            curveToRelative(5.1f, 0.7f, 11.0f, 2.4f, 12.8f, 3.7f)
            curveToRelative(0.8f, 0.6f, 0.8f, 2.4f, -0.1f, 6.9f)
            curveToRelative(-0.7f, 3.3f, -1.5f, 6.3f, -1.8f, 6.6f)
            curveToRelative(-0.4f, 0.3f, -2.9f, -0.2f, -5.6f, -1.1f)
            curveToRelative(-2.9f, -0.9f, -8.3f, -1.6f, -13.0f, -1.6f)
            curveToRelative(-7.4f, -0.0f, -8.1f, 0.2f, -10.0f, 2.6f)
            curveToRelative(-5.4f, 6.9f, -1.5f, 10.7f, 18.4f, 18.1f)
            curveToRelative(3.2f, 1.1f, 7.0f, 3.8f, 10.7f, 7.4f)
            curveToRelative(5.1f, 5.1f, 5.7f, 6.0f, 6.3f, 11.1f)
            curveToRelative(0.8f, 6.5f, 0.1f, 10.7f, -2.7f, 15.7f)
            curveToRelative(-2.5f, 4.4f, -4.3f, 6.2f, -8.8f, 8.5f)
            curveToRelative(-6.3f, 3.4f, -6.9f, 3.6f, -9.5f, 3.6f)
            curveToRelative(-2.4f, -0.0f, -2.5f, 0.2f, -2.5f, 6.5f)
            lineToRelative(0.0f, 6.5f)
            lineToRelative(-6.0f, -0.0f)
            lineToRelative(-6.0f, -0.0f)
            lineToRelative(0.0f, -5.3f)
            lineToRelative(0.0f, -5.3f)
            lineToRelative(-6.7f, -1.3f)
            curveToRelative(-3.8f, -0.7f, -8.8f, -2.0f, -11.1f, -2.9f)
            curveToRelative(-4.1f, -1.4f, -4.3f, -1.7f, -3.9f, -4.6f)
            curveToRelative(0.6f, -3.9f, 2.4f, -10.6f, 2.9f, -11.2f)
            curveToRelative(0.3f, -0.2f, 3.1f, 0.6f, 6.4f, 1.7f)
            curveToRelative(8.5f, 3.1f, 21.2f, 3.2f, 25.1f, 0.3f)
            curveToRelative(3.9f, -2.9f, 4.4f, -8.7f, 1.1f, -11.6f)
            curveToRelative(-2.6f, -2.2f, -8.0f, -5.1f, -11.8f, -6.3f)
            curveToRelative(-5.4f, -1.7f, -14.6f, -6.5f, -17.4f, -9.1f)
            curveToRelative(-1.5f, -1.5f, -3.7f, -4.5f, -4.7f, -6.8f)
            curveToRelative(-5.5f, -12.1f, 1.8f, -25.7f, 16.4f, -30.5f)
            lineToRelative(3.7f, -1.3f)
            lineToRelative(0.0f, -5.9f)
            lineToRelative(0.0f, -5.9f)
            lineToRelative(6.5f, -0.0f)
            lineToRelative(6.5f, -0.0f)
            lineToRelative(0.0f, 5.4f)
            close()
        }
        path(
            fill = SolidColor(groupColors[0]), stroke = SolidColor(groupColors[1]),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(296.3f, 98.8f)
            curveToRelative(-3.6f, 5.8f, -30.4f, 14.5f, -52.8f, 17.2f)
            curveToRelative(-30.4f, 3.6f, -59.4f, 4.5f, -80.0f, 2.3f)
            lineToRelative(-5.9f, -0.6f)
            lineToRelative(1.9f, 2.9f)
            curveToRelative(2.3f, 3.4f, 7.9f, 14.9f, 8.9f, 18.1f)
            curveToRelative(0.7f, 2.3f, 0.8f, 2.3f, 21.9f, 2.3f)
            curveToRelative(25.8f, -0.0f, 44.5f, -1.5f, 67.7f, -5.6f)
            curveToRelative(9.6f, -1.7f, 16.6f, -3.2f, 19.5f, -4.4f)
            curveToRelative(1.1f, -0.4f, 4.5f, -1.5f, 7.5f, -2.5f)
            curveToRelative(3.0f, -1.0f, 7.4f, -2.7f, 9.8f, -3.8f)
            lineToRelative(4.2f, -1.9f)
            lineToRelative(0.0f, -12.9f)
            curveToRelative(0.0f, -7.1f, -0.3f, -12.9f, -0.8f, -12.9f)
            curveToRelative(-0.4f, -0.0f, -1.2f, 0.8f, -1.9f, 1.8f)
            close()
        }
        path(
            fill = SolidColor(groupColors[0]), stroke = SolidColor(groupColors[1]),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(295.2f, 134.5f)
            curveToRelative(-4.3f, 4.3f, -19.0f, 10.3f, -30.7f, 12.5f)
            curveToRelative(-13.3f, 2.6f, -16.3f, 3.1f, -22.5f, 4.1f)
            curveToRelative(-12.8f, 1.8f, -29.8f, 2.9f, -49.2f, 2.9f)
            lineToRelative(-19.8f, -0.0f)
            lineToRelative(0.0f, 2.8f)
            curveToRelative(0.0f, 1.6f, 0.3f, 6.3f, 0.6f, 10.5f)
            lineToRelative(0.7f, 7.6f)
            lineToRelative(6.2f, 0.6f)
            curveToRelative(6.6f, 0.7f, 46.2f, -0.9f, 60.5f, -2.5f)
            curveToRelative(20.2f, -2.2f, 39.1f, -6.9f, 52.3f, -12.9f)
            lineToRelative(5.7f, -2.6f)
            lineToRelative(0.0f, -12.7f)
            curveToRelative(0.0f, -7.1f, -0.3f, -12.8f, -0.7f, -12.7f)
            curveToRelative(-0.5f, 0.1f, -1.8f, 1.1f, -3.1f, 2.4f)
            close()
        }
        path(
            fill = SolidColor(groupColors[0]), stroke = SolidColor(groupColors[1]),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(295.9f, 168.7f)
            curveToRelative(-6.0f, 6.3f, -25.9f, 13.2f, -47.9f, 16.4f)
            curveToRelative(-16.5f, 2.4f, -23.6f, 3.0f, -49.1f, 3.5f)
            lineToRelative(-26.7f, 0.6f)
            lineToRelative(-1.5f, 5.2f)
            curveToRelative(-0.8f, 2.8f, -2.4f, 7.4f, -3.5f, 10.3f)
            lineToRelative(-2.1f, 5.1f)
            lineToRelative(12.7f, 0.7f)
            curveToRelative(8.8f, 0.6f, 21.2f, 0.2f, 40.2f, -1.0f)
            curveToRelative(15.1f, -0.9f, 27.8f, -1.9f, 28.1f, -2.1f)
            curveToRelative(0.4f, -0.2f, 3.5f, -0.8f, 7.0f, -1.4f)
            curveToRelative(20.3f, -3.1f, 44.1f, -10.6f, 45.5f, -14.4f)
            curveToRelative(0.2f, -0.6f, 0.3f, -6.7f, 0.2f, -13.4f)
            lineToRelative(-0.3f, -12.2f)
            lineToRelative(-2.6f, 2.7f)
            close()
        }
        path(
            fill = SolidColor(groupColors[0]), stroke = SolidColor(groupColors[1]),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(296.3f, 203.4f)
            curveToRelative(-3.6f, 3.6f, -4.5f, 4.1f, -13.8f, 7.8f)
            curveToRelative(-9.6f, 3.9f, -19.6f, 6.3f, -39.0f, 9.7f)
            curveToRelative(-11.1f, 1.9f, -46.6f, 3.4f, -66.6f, 2.7f)
            lineToRelative(-19.6f, -0.7f)
            lineToRelative(-1.8f, 2.8f)
            curveToRelative(-1.0f, 1.5f, -4.9f, 5.9f, -8.6f, 9.7f)
            curveToRelative(-3.8f, 3.9f, -6.7f, 7.1f, -6.5f, 7.3f)
            curveToRelative(0.8f, 0.8f, 38.9f, 3.3f, 50.6f, 3.3f)
            curveToRelative(27.0f, -0.1f, 64.4f, -4.2f, 81.5f, -9.0f)
            curveToRelative(2.2f, -0.6f, 5.6f, -1.5f, 7.5f, -2.0f)
            curveToRelative(4.2f, -1.2f, 9.1f, -3.0f, 14.8f, -5.5f)
            lineToRelative(4.2f, -2.0f)
            lineToRelative(0.0f, -13.2f)
            curveToRelative(0.0f, -7.3f, -0.1f, -13.3f, -0.2f, -13.2f)
            curveToRelative(-0.2f, 0.1f, -1.3f, 1.1f, -2.5f, 2.3f)
            close()
        }
    }
        .build()