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

fun getSimpleDotsVector(groupColors: List<Color>): ImageVector =
    Builder(
        name = "IconShDotsprojector",
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
            moveTo(26.0f, 3.6f)
            curveToRelative(-9.0f, 3.0f, -17.1f, 9.8f, -20.5f, 17.4f)
            curveToRelative(-2.8f, 6.2f, -2.8f, 23.8f, -0.1f, 29.0f)
            curveToRelative(1.0f, 1.9f, 2.5f, 3.9f, 3.2f, 4.3f)
            curveToRelative(0.8f, 0.4f, 1.4f, 1.4f, 1.4f, 2.2f)
            curveToRelative(0.0f, 0.8f, 1.2f, 2.3f, 2.7f, 3.2f)
            curveToRelative(1.5f, 1.0f, 3.6f, 2.6f, 4.5f, 3.5f)
            curveToRelative(1.0f, 1.0f, 3.0f, 2.1f, 4.5f, 2.4f)
            curveToRelative(1.6f, 0.4f, 3.1f, 1.2f, 3.5f, 1.8f)
            curveToRelative(0.5f, 0.7f, 4.5f, 1.1f, 10.6f, 1.1f)
            curveToRelative(8.0f, -0.0f, 10.2f, -0.3f, 11.6f, -1.8f)
            curveToRelative(1.0f, -0.9f, 2.4f, -1.7f, 3.2f, -1.7f)
            curveToRelative(4.2f, -0.0f, 14.4f, -10.2f, 14.4f, -14.4f)
            curveToRelative(0.0f, -0.8f, 0.8f, -2.2f, 1.8f, -3.2f)
            curveToRelative(1.4f, -1.4f, 1.7f, -3.6f, 1.7f, -11.7f)
            curveToRelative(0.0f, -7.1f, -0.4f, -10.2f, -1.2f, -10.4f)
            curveToRelative(-0.7f, -0.3f, -1.3f, -1.1f, -1.3f, -1.9f)
            curveToRelative(0.0f, -3.5f, -8.0f, -14.4f, -10.6f, -14.4f)
            curveToRelative(-0.6f, -0.0f, -1.4f, -0.6f, -1.7f, -1.3f)
            curveToRelative(-1.2f, -3.3f, -21.1f, -6.2f, -27.7f, -4.1f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(138.0f, 4.6f)
            curveToRelative(-1.9f, 0.8f, -4.3f, 1.7f, -5.2f, 2.0f)
            curveToRelative(-1.0f, 0.4f, -1.8f, 0.9f, -1.8f, 1.3f)
            curveToRelative(0.0f, 0.4f, -1.3f, 1.4f, -2.8f, 2.3f)
            curveToRelative(-1.6f, 0.9f, -3.7f, 3.4f, -4.8f, 5.5f)
            curveToRelative(-1.0f, 2.1f, -2.3f, 4.1f, -2.8f, 4.4f)
            curveToRelative(-1.8f, 1.4f, -3.6f, 8.6f, -3.6f, 15.0f)
            curveToRelative(0.0f, 6.7f, 2.4f, 16.9f, 3.9f, 16.9f)
            curveToRelative(0.5f, -0.0f, 1.4f, 1.3f, 1.9f, 2.9f)
            curveToRelative(0.6f, 1.6f, 2.3f, 3.8f, 3.9f, 4.8f)
            curveToRelative(1.5f, 1.0f, 3.6f, 2.6f, 4.5f, 3.5f)
            curveToRelative(1.0f, 1.0f, 3.0f, 2.1f, 4.5f, 2.4f)
            curveToRelative(1.6f, 0.4f, 3.1f, 1.2f, 3.5f, 1.8f)
            curveToRelative(1.0f, 1.5f, 21.6f, 1.5f, 22.6f, -0.0f)
            curveToRelative(0.4f, -0.6f, 2.4f, -1.9f, 4.4f, -2.8f)
            curveToRelative(6.5f, -3.1f, 13.8f, -11.8f, 13.8f, -16.4f)
            curveToRelative(0.0f, -0.6f, 0.6f, -1.2f, 1.3f, -1.4f)
            curveToRelative(0.8f, -0.3f, 1.3f, -3.5f, 1.5f, -10.0f)
            curveToRelative(0.3f, -7.6f, 0.1f, -9.8f, -1.2f, -11.1f)
            curveToRelative(-0.9f, -0.8f, -1.6f, -2.1f, -1.6f, -2.7f)
            curveToRelative(0.0f, -3.4f, -8.1f, -14.0f, -10.6f, -14.0f)
            curveToRelative(-0.7f, -0.0f, -1.4f, -0.6f, -1.7f, -1.3f)
            curveToRelative(-1.5f, -4.0f, -22.1f, -6.1f, -29.7f, -3.1f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(254.0f, 3.7f)
            curveToRelative(-4.4f, 1.6f, -8.8f, 4.0f, -11.0f, 6.0f)
            curveToRelative(-1.4f, 1.3f, -2.7f, 2.3f, -3.0f, 2.3f)
            curveToRelative(-0.3f, -0.0f, -1.4f, 1.7f, -2.5f, 3.7f)
            curveToRelative(-1.1f, 2.1f, -2.3f, 4.0f, -2.7f, 4.3f)
            curveToRelative(-0.3f, 0.3f, -1.3f, 2.5f, -2.2f, 5.0f)
            curveToRelative(-3.1f, 8.8f, -1.3f, 23.2f, 3.5f, 28.3f)
            curveToRelative(1.0f, 1.1f, 2.1f, 2.9f, 2.5f, 3.9f)
            curveToRelative(0.3f, 1.0f, 1.2f, 1.8f, 1.9f, 1.8f)
            curveToRelative(0.8f, -0.0f, 2.2f, 1.1f, 3.1f, 2.4f)
            curveToRelative(0.8f, 1.4f, 3.2f, 3.0f, 5.2f, 3.7f)
            curveToRelative(2.0f, 0.7f, 4.0f, 1.7f, 4.4f, 2.3f)
            curveToRelative(1.1f, 1.6f, 21.0f, 1.5f, 21.6f, -0.2f)
            curveToRelative(0.2f, -0.6f, 1.0f, -1.2f, 1.9f, -1.2f)
            curveToRelative(3.9f, -0.0f, 13.1f, -7.6f, 15.3f, -12.8f)
            curveToRelative(0.8f, -1.7f, 1.8f, -3.2f, 2.2f, -3.2f)
            curveToRelative(1.4f, -0.0f, 2.8f, -7.6f, 2.8f, -15.1f)
            curveToRelative(0.0f, -7.5f, -1.4f, -13.9f, -3.0f, -13.9f)
            curveToRelative(-0.5f, -0.0f, -1.2f, -1.0f, -1.5f, -2.3f)
            curveToRelative(-1.3f, -4.2f, -11.0f, -12.5f, -16.7f, -14.2f)
            curveToRelative(-4.9f, -1.4f, -18.6f, -2.0f, -21.8f, -0.8f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(25.6f, 118.4f)
            curveToRelative(-2.6f, 0.7f, -5.1f, 1.7f, -5.5f, 2.2f)
            curveToRelative(-0.3f, 0.5f, -2.3f, 1.8f, -4.4f, 2.9f)
            curveToRelative(-2.1f, 1.1f, -4.3f, 2.8f, -4.8f, 3.7f)
            curveToRelative(-0.5f, 0.9f, -1.5f, 2.2f, -2.1f, 3.0f)
            curveToRelative(-1.7f, 1.7f, -3.3f, 5.3f, -4.9f, 10.2f)
            curveToRelative(-2.3f, 7.5f, 0.7f, 27.6f, 4.1f, 27.6f)
            curveToRelative(0.6f, -0.0f, 1.0f, 0.6f, 1.0f, 1.3f)
            curveToRelative(0.0f, 1.7f, 5.7f, 7.0f, 10.0f, 9.2f)
            curveToRelative(1.9f, 1.0f, 3.8f, 2.3f, 4.2f, 2.9f)
            curveToRelative(1.0f, 1.5f, 23.6f, 1.5f, 24.6f, -0.0f)
            curveToRelative(0.4f, -0.7f, 2.4f, -1.9f, 4.5f, -2.7f)
            curveToRelative(5.8f, -2.5f, 13.7f, -12.0f, 13.7f, -16.5f)
            curveToRelative(0.0f, -0.6f, 0.6f, -1.2f, 1.3f, -1.4f)
            curveToRelative(1.6f, -0.6f, 1.7f, -20.5f, 0.1f, -21.6f)
            curveToRelative(-0.6f, -0.4f, -1.7f, -2.4f, -2.4f, -4.5f)
            curveToRelative(-0.6f, -2.1f, -1.9f, -4.1f, -2.7f, -4.4f)
            curveToRelative(-0.8f, -0.3f, -1.8f, -1.6f, -2.3f, -2.9f)
            curveToRelative(-0.5f, -1.2f, -2.5f, -3.0f, -4.4f, -3.9f)
            curveToRelative(-2.0f, -1.0f, -3.6f, -2.1f, -3.6f, -2.5f)
            curveToRelative(0.0f, -2.9f, -18.7f, -4.8f, -26.4f, -2.6f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(139.6f, 118.4f)
            curveToRelative(-2.6f, 0.7f, -5.1f, 1.8f, -5.5f, 2.3f)
            curveToRelative(-0.3f, 0.5f, -1.3f, 1.3f, -2.1f, 1.7f)
            curveToRelative(-5.6f, 2.7f, -7.8f, 4.9f, -9.6f, 9.8f)
            curveToRelative(-0.3f, 1.0f, -1.0f, 1.8f, -1.4f, 1.8f)
            curveToRelative(-1.6f, -0.0f, -4.0f, 9.6f, -4.0f, 16.1f)
            curveToRelative(0.0f, 7.0f, 1.9f, 13.9f, 4.5f, 16.5f)
            curveToRelative(0.8f, 0.9f, 1.5f, 2.1f, 1.5f, 2.8f)
            curveToRelative(0.0f, 1.8f, 6.2f, 7.3f, 10.6f, 9.3f)
            curveToRelative(2.1f, 1.0f, 4.2f, 2.2f, 4.6f, 2.8f)
            curveToRelative(1.0f, 1.4f, 22.7f, 1.3f, 23.6f, -0.1f)
            curveToRelative(0.4f, -0.7f, 2.4f, -1.9f, 4.5f, -2.8f)
            curveToRelative(4.9f, -2.0f, 11.2f, -8.6f, 12.8f, -13.4f)
            curveToRelative(0.6f, -2.0f, 1.7f, -4.0f, 2.3f, -4.4f)
            curveToRelative(0.6f, -0.4f, 1.2f, -4.7f, 1.4f, -10.1f)
            curveToRelative(0.3f, -7.6f, 0.1f, -9.7f, -1.2f, -11.0f)
            curveToRelative(-0.9f, -0.8f, -1.6f, -2.2f, -1.6f, -2.9f)
            curveToRelative(0.0f, -3.1f, -8.2f, -13.8f, -10.6f, -13.8f)
            curveToRelative(-0.7f, -0.0f, -1.9f, -0.6f, -2.6f, -1.3f)
            curveToRelative(-4.2f, -4.0f, -18.4f, -5.8f, -27.2f, -3.3f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(253.5f, 118.5f)
            curveToRelative(-2.7f, 0.7f, -5.1f, 1.8f, -5.3f, 2.4f)
            curveToRelative(-0.2f, 0.6f, -0.9f, 1.1f, -1.5f, 1.1f)
            curveToRelative(-1.5f, -0.0f, -10.7f, 9.3f, -10.7f, 10.8f)
            curveToRelative(0.0f, 0.7f, -0.4f, 1.2f, -0.9f, 1.2f)
            curveToRelative(-1.6f, -0.0f, -4.1f, 9.7f, -4.1f, 15.8f)
            curveToRelative(0.0f, 6.5f, 2.6f, 16.2f, 4.7f, 17.5f)
            curveToRelative(0.7f, 0.4f, 1.3f, 1.3f, 1.3f, 1.9f)
            curveToRelative(0.0f, 1.8f, 7.4f, 8.3f, 11.2f, 9.8f)
            curveToRelative(1.8f, 0.8f, 3.6f, 1.9f, 4.0f, 2.4f)
            curveToRelative(0.4f, 0.7f, 5.1f, 1.1f, 11.8f, 1.1f)
            curveToRelative(6.7f, -0.0f, 11.4f, -0.4f, 11.8f, -1.1f)
            curveToRelative(0.4f, -0.5f, 2.2f, -1.6f, 4.1f, -2.4f)
            curveToRelative(4.3f, -1.7f, 11.9f, -9.2f, 12.7f, -12.5f)
            curveToRelative(0.3f, -1.4f, 0.9f, -2.5f, 1.3f, -2.5f)
            curveToRelative(1.8f, -0.0f, 3.1f, -5.7f, 3.1f, -13.2f)
            curveToRelative(0.0f, -8.1f, -1.4f, -14.1f, -3.7f, -16.6f)
            curveToRelative(-0.7f, -0.7f, -1.3f, -1.7f, -1.3f, -2.2f)
            curveToRelative(0.0f, -1.3f, -6.3f, -8.0f, -7.5f, -8.0f)
            curveToRelative(-0.7f, -0.0f, -2.3f, -1.1f, -3.6f, -2.4f)
            curveToRelative(-4.3f, -4.3f, -17.5f, -5.8f, -27.4f, -3.1f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(26.5f, 232.4f)
            curveToRelative(-3.2f, 0.7f, -6.1f, 1.8f, -6.3f, 2.5f)
            curveToRelative(-0.2f, 0.6f, -0.9f, 1.1f, -1.6f, 1.1f)
            curveToRelative(-0.7f, -0.0f, -2.4f, 1.2f, -3.7f, 2.8f)
            curveToRelative(-1.4f, 1.5f, -3.7f, 3.7f, -5.1f, 4.7f)
            curveToRelative(-3.7f, 3.0f, -6.8f, 12.2f, -6.8f, 20.4f)
            curveToRelative(0.0f, 10.4f, 2.1f, 16.1f, 8.7f, 23.1f)
            curveToRelative(3.2f, 3.3f, 6.5f, 6.0f, 7.4f, 6.0f)
            curveToRelative(0.9f, -0.0f, 1.9f, 0.6f, 2.2f, 1.3f)
            curveToRelative(0.3f, 0.8f, 3.1f, 1.9f, 6.4f, 2.6f)
            curveToRelative(6.8f, 1.5f, 20.1f, 0.1f, 21.8f, -2.2f)
            curveToRelative(0.5f, -0.8f, 2.0f, -1.7f, 3.3f, -2.1f)
            curveToRelative(4.5f, -1.5f, 13.2f, -11.9f, 13.2f, -16.0f)
            curveToRelative(0.0f, -0.8f, 0.6f, -1.6f, 1.3f, -1.8f)
            curveToRelative(1.5f, -0.6f, 1.7f, -20.5f, 0.1f, -21.6f)
            curveToRelative(-0.5f, -0.4f, -1.7f, -2.3f, -2.4f, -4.2f)
            curveToRelative(-1.3f, -3.1f, -6.0f, -8.3f, -11.8f, -13.1f)
            curveToRelative(-4.6f, -3.9f, -17.4f, -5.5f, -26.7f, -3.5f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(143.5f, 232.1f)
            curveToRelative(-7.5f, 1.5f, -9.5f, 2.1f, -9.5f, 3.0f)
            curveToRelative(0.0f, 0.5f, -0.5f, 0.9f, -1.2f, 0.9f)
            curveToRelative(-1.5f, -0.0f, -10.8f, 9.2f, -10.8f, 10.7f)
            curveToRelative(0.0f, 0.7f, -0.4f, 1.3f, -0.9f, 1.3f)
            curveToRelative(-1.7f, -0.0f, -4.1f, 9.3f, -4.1f, 15.9f)
            curveToRelative(0.0f, 6.8f, 1.9f, 14.9f, 4.2f, 17.7f)
            curveToRelative(4.6f, 5.6f, 9.4f, 10.4f, 10.5f, 10.4f)
            curveToRelative(0.7f, -0.0f, 2.0f, 0.9f, 3.0f, 1.9f)
            curveToRelative(1.0f, 1.0f, 4.3f, 2.4f, 7.3f, 3.0f)
            curveToRelative(8.2f, 1.7f, 22.0f, -0.1f, 22.0f, -2.8f)
            curveToRelative(0.0f, -0.5f, 1.1f, -1.2f, 2.5f, -1.5f)
            curveToRelative(3.3f, -0.8f, 10.8f, -8.4f, 12.5f, -12.7f)
            curveToRelative(0.8f, -1.9f, 1.9f, -3.7f, 2.4f, -4.1f)
            curveToRelative(1.5f, -1.0f, 1.5f, -21.6f, 0.0f, -22.6f)
            curveToRelative(-0.5f, -0.4f, -1.6f, -2.3f, -2.4f, -4.2f)
            curveToRelative(-1.2f, -3.1f, -5.8f, -8.2f, -11.7f, -13.0f)
            curveToRelative(-4.3f, -3.7f, -15.9f, -5.6f, -23.8f, -3.9f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(257.5f, 232.0f)
            curveToRelative(-7.8f, 1.7f, -9.5f, 2.2f, -9.5f, 3.1f)
            curveToRelative(0.0f, 0.5f, -0.5f, 0.9f, -1.1f, 0.9f)
            curveToRelative(-1.5f, -0.0f, -10.8f, 9.2f, -10.9f, 10.7f)
            curveToRelative(0.0f, 0.6f, -0.5f, 1.3f, -1.0f, 1.5f)
            curveToRelative(-1.5f, 0.5f, -4.0f, 10.5f, -4.0f, 16.3f)
            curveToRelative(0.0f, 5.8f, 2.2f, 14.6f, 4.1f, 17.0f)
            curveToRelative(4.5f, 5.3f, 9.7f, 10.5f, 10.7f, 10.5f)
            curveToRelative(0.6f, -0.0f, 2.0f, 0.9f, 3.0f, 2.0f)
            curveToRelative(1.3f, 1.4f, 4.2f, 2.3f, 10.0f, 3.2f)
            curveToRelative(7.1f, 1.0f, 9.1f, 0.9f, 14.2f, -0.5f)
            curveToRelative(6.4f, -1.8f, 8.0f, -2.9f, 14.5f, -9.6f)
            curveToRelative(8.5f, -8.8f, 9.5f, -11.2f, 9.5f, -22.6f)
            curveToRelative(0.0f, -8.0f, -1.5f, -14.4f, -3.7f, -15.8f)
            curveToRelative(-0.7f, -0.4f, -1.3f, -1.3f, -1.3f, -2.0f)
            curveToRelative(0.0f, -1.9f, -10.7f, -11.4f, -15.1f, -13.4f)
            curveToRelative(-3.6f, -1.6f, -14.6f, -2.3f, -19.4f, -1.3f)
            close()
        }
    }
        .build()