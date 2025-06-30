package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

fun getSimpleFirelightVector(groupColors: List<Color>): ImageVector =
    Builder(
        name = "IconShFirelight",
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
            moveTo(145.0f, 3.9f)
            curveToRelative(-6.5f, 1.4f, -15.0f, 7.2f, -18.7f, 12.7f)
            curveToRelative(-4.3f, 6.4f, -4.7f, 7.9f, -4.7f, 17.9f)
            curveToRelative(0.0f, 9.2f, 0.2f, 10.4f, 2.8f, 14.8f)
            curveToRelative(1.5f, 2.6f, 4.6f, 6.2f, 6.7f, 8.1f)
            lineToRelative(4.0f, 3.3f)
            lineToRelative(-2.5f, 1.7f)
            curveToRelative(-1.4f, 0.9f, -2.9f, 1.6f, -3.4f, 1.6f)
            curveToRelative(-0.5f, -0.0f, -1.7f, 0.9f, -2.7f, 2.0f)
            curveToRelative(-1.0f, 1.1f, -2.7f, 2.2f, -3.9f, 2.6f)
            curveToRelative(-1.1f, 0.3f, -3.5f, 1.7f, -5.3f, 3.0f)
            curveToRelative(-1.7f, 1.3f, -3.5f, 2.4f, -3.8f, 2.4f)
            curveToRelative(-0.3f, -0.0f, -2.1f, 1.1f, -3.9f, 2.5f)
            curveToRelative(-1.8f, 1.4f, -3.6f, 2.5f, -4.1f, 2.5f)
            curveToRelative(-0.4f, -0.0f, -2.0f, 1.1f, -3.7f, 2.5f)
            curveToRelative(-1.6f, 1.4f, -3.5f, 2.5f, -4.1f, 2.5f)
            curveToRelative(-0.7f, -0.0f, -2.4f, 1.1f, -3.9f, 2.5f)
            curveToRelative(-1.5f, 1.4f, -3.2f, 2.5f, -3.7f, 2.5f)
            curveToRelative(-0.5f, -0.0f, -4.0f, 2.1f, -7.7f, 4.6f)
            curveToRelative(-3.6f, 2.5f, -7.4f, 4.8f, -8.3f, 5.1f)
            curveToRelative(-0.9f, 0.3f, -2.5f, 1.4f, -3.6f, 2.4f)
            curveToRelative(-1.1f, 0.9f, -3.4f, 2.5f, -5.0f, 3.6f)
            curveToRelative(-7.8f, 4.7f, -9.5f, 5.9f, -9.5f, 6.5f)
            curveToRelative(0.0f, 0.5f, 43.2f, 0.8f, 96.0f, 0.8f)
            curveToRelative(52.8f, -0.0f, 96.0f, -0.3f, 96.0f, -0.8f)
            curveToRelative(0.0f, -0.7f, -2.5f, -2.4f, -10.9f, -7.4f)
            curveToRelative(-2.5f, -1.4f, -4.7f, -3.1f, -4.9f, -3.7f)
            curveToRelative(-0.2f, -0.6f, -1.0f, -1.1f, -1.7f, -1.1f)
            curveToRelative(-0.7f, -0.0f, -4.6f, -2.2f, -8.7f, -5.0f)
            curveToRelative(-4.0f, -2.7f, -7.8f, -5.0f, -8.4f, -5.0f)
            curveToRelative(-0.6f, -0.0f, -1.7f, -0.7f, -2.5f, -1.6f)
            curveToRelative(-0.7f, -0.9f, -2.7f, -2.3f, -4.5f, -3.2f)
            curveToRelative(-1.7f, -0.9f, -5.0f, -3.0f, -7.3f, -4.5f)
            curveToRelative(-2.3f, -1.5f, -5.9f, -3.7f, -8.1f, -4.8f)
            curveToRelative(-2.1f, -1.1f, -4.4f, -2.6f, -5.0f, -3.4f)
            curveToRelative(-0.6f, -0.7f, -2.5f, -1.9f, -4.3f, -2.6f)
            curveToRelative(-1.8f, -0.7f, -3.8f, -2.1f, -4.5f, -3.1f)
            curveToRelative(-0.7f, -1.0f, -2.1f, -1.8f, -3.1f, -1.8f)
            curveToRelative(-1.0f, -0.0f, -2.5f, -0.8f, -3.4f, -1.8f)
            curveToRelative(-1.5f, -1.6f, -1.4f, -2.0f, 1.8f, -4.5f)
            curveToRelative(1.9f, -1.5f, 4.0f, -3.8f, 4.7f, -5.1f)
            curveToRelative(0.7f, -1.3f, 2.2f, -3.9f, 3.2f, -5.7f)
            curveToRelative(1.5f, -2.7f, 1.9f, -5.4f, 1.9f, -12.4f)
            curveToRelative(0.0f, -7.0f, -0.4f, -9.7f, -1.9f, -12.4f)
            curveToRelative(-1.0f, -1.8f, -2.4f, -4.3f, -3.1f, -5.5f)
            curveToRelative(-1.8f, -3.2f, -6.2f, -7.1f, -11.4f, -9.9f)
            curveToRelative(-5.1f, -2.8f, -14.9f, -4.1f, -20.9f, -2.8f)
            close()
            moveTo(159.0f, 20.7f)
            curveToRelative(5.9f, 4.1f, 7.3f, 6.0f, 7.8f, 11.2f)
            curveToRelative(0.6f, 6.5f, -0.1f, 8.9f, -3.7f, 13.0f)
            curveToRelative(-3.5f, 3.9f, -9.2f, 5.4f, -15.9f, 4.1f)
            curveToRelative(-4.1f, -0.7f, -5.2f, -1.6f, -8.9f, -6.8f)
            curveToRelative(-2.3f, -3.4f, -2.1f, -12.6f, 0.4f, -16.0f)
            curveToRelative(5.0f, -6.8f, 14.7f, -9.4f, 20.3f, -5.5f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(55.0f, 120.6f)
            curveToRelative(0.0f, 1.1f, 7.1f, 26.9f, 9.6f, 34.6f)
            curveToRelative(1.3f, 4.3f, 2.4f, 8.5f, 2.4f, 9.5f)
            curveToRelative(0.0f, 1.0f, 1.1f, 5.4f, 2.4f, 9.8f)
            curveToRelative(4.1f, 13.4f, 7.6f, 26.9f, 7.6f, 29.0f)
            curveToRelative(0.0f, 1.2f, 0.4f, 2.5f, 0.9f, 3.0f)
            curveToRelative(0.5f, 0.6f, 1.7f, 4.1f, 2.6f, 8.0f)
            curveToRelative(2.6f, 10.7f, 3.8f, 14.9f, 4.6f, 16.0f)
            curveToRelative(0.4f, 0.5f, 1.0f, 2.8f, 1.4f, 5.0f)
            curveToRelative(0.8f, 4.6f, 4.8f, 19.7f, 8.0f, 30.0f)
            lineToRelative(2.2f, 7.0f)
            lineToRelative(55.1f, 0.3f)
            lineToRelative(55.0f, 0.2f)
            lineToRelative(2.5f, -7.7f)
            curveToRelative(1.4f, -4.3f, 2.7f, -9.7f, 3.1f, -11.9f)
            curveToRelative(0.3f, -2.3f, 1.5f, -6.1f, 2.6f, -8.5f)
            curveToRelative(1.1f, -2.4f, 2.0f, -5.5f, 2.0f, -6.8f)
            curveToRelative(0.0f, -1.4f, 1.1f, -6.1f, 2.4f, -10.5f)
            curveToRelative(6.4f, -21.9f, 7.6f, -26.5f, 7.6f, -28.5f)
            curveToRelative(0.0f, -1.2f, 0.7f, -3.6f, 1.6f, -5.2f)
            curveToRelative(0.8f, -1.7f, 2.4f, -7.3f, 3.5f, -12.5f)
            curveToRelative(1.1f, -5.1f, 2.7f, -10.6f, 3.5f, -12.1f)
            curveToRelative(0.7f, -1.6f, 1.4f, -3.6f, 1.4f, -4.6f)
            curveToRelative(0.0f, -1.7f, 2.2f, -9.8f, 7.5f, -28.2f)
            curveToRelative(1.3f, -4.4f, 2.6f, -9.9f, 3.0f, -12.3f)
            lineToRelative(0.7f, -4.2f)
            lineToRelative(-96.6f, -0.0f)
            curveToRelative(-53.1f, -0.0f, -96.6f, 0.3f, -96.6f, 0.6f)
            close()
            moveTo(226.0f, 138.2f)
            curveToRelative(-0.1f, 0.7f, -0.7f, 3.1f, -1.5f, 5.3f)
            curveToRelative(-0.8f, 2.2f, -2.0f, 6.7f, -2.6f, 10.0f)
            curveToRelative(-0.6f, 3.3f, -1.4f, 6.4f, -1.8f, 7.0f)
            curveToRelative(-0.4f, 0.5f, -1.3f, 3.0f, -1.9f, 5.5f)
            curveToRelative(-3.5f, 14.8f, -4.6f, 18.6f, -5.3f, 19.4f)
            curveToRelative(-0.5f, 0.6f, -0.9f, 1.9f, -0.9f, 3.0f)
            curveToRelative(0.0f, 1.9f, -3.0f, 13.2f, -7.6f, 28.1f)
            curveToRelative(-1.3f, 4.4f, -2.4f, 9.0f, -2.4f, 10.2f)
            curveToRelative(0.0f, 1.2f, -1.1f, 5.3f, -2.5f, 9.1f)
            curveToRelative(-1.4f, 3.8f, -2.5f, 7.6f, -2.5f, 8.4f)
            curveToRelative(0.0f, 0.8f, -0.7f, 3.8f, -1.5f, 6.6f)
            lineToRelative(-1.6f, 5.2f)
            lineToRelative(-42.0f, -0.0f)
            curveToRelative(-39.1f, -0.0f, -42.0f, -0.1f, -42.5f, -1.8f)
            curveToRelative(-0.3f, -0.9f, -1.0f, -2.6f, -1.4f, -3.7f)
            curveToRelative(-0.5f, -1.1f, -1.1f, -3.4f, -1.3f, -5.0f)
            curveToRelative(-0.3f, -1.7f, -1.9f, -8.0f, -3.7f, -14.0f)
            curveToRelative(-1.7f, -6.1f, -3.8f, -13.9f, -4.7f, -17.5f)
            curveToRelative(-0.8f, -3.6f, -2.6f, -9.7f, -3.9f, -13.5f)
            curveToRelative(-1.3f, -3.9f, -2.4f, -7.9f, -2.4f, -8.9f)
            curveToRelative(0.0f, -1.8f, -1.3f, -6.6f, -7.6f, -28.2f)
            curveToRelative(-1.3f, -4.4f, -2.4f, -8.9f, -2.4f, -10.0f)
            curveToRelative(0.0f, -1.0f, -1.1f, -4.6f, -2.5f, -7.9f)
            curveToRelative(-1.3f, -3.3f, -2.5f, -6.6f, -2.5f, -7.3f)
            curveToRelative(0.0f, -0.9f, 15.6f, -1.2f, 74.5f, -1.2f)
            curveToRelative(59.0f, -0.0f, 74.5f, 0.3f, 74.5f, 1.2f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(146.0f, 161.8f)
            curveToRelative(0.0f, 7.1f, -2.1f, 15.7f, -6.0f, 24.1f)
            curveToRelative(-1.7f, 3.5f, -3.0f, 6.9f, -3.0f, 7.6f)
            curveToRelative(0.0f, 0.7f, -0.6f, 1.8f, -1.4f, 2.4f)
            curveToRelative(-1.1f, 0.9f, -1.5f, 0.2f, -2.1f, -4.4f)
            curveToRelative(-0.8f, -6.4f, -2.1f, -7.8f, -3.0f, -3.3f)
            curveToRelative(-0.4f, 1.8f, -1.3f, 4.6f, -2.0f, 6.3f)
            curveToRelative(-6.3f, 14.8f, -7.7f, 30.4f, -3.5f, 39.0f)
            curveToRelative(3.5f, 7.0f, 7.3f, 12.5f, 8.7f, 12.5f)
            curveToRelative(0.6f, -0.0f, 1.8f, 0.7f, 2.7f, 1.5f)
            curveToRelative(2.5f, 2.6f, 6.5f, 3.5f, 14.8f, 3.5f)
            curveToRelative(7.1f, -0.0f, 8.7f, -0.3f, 13.4f, -3.0f)
            curveToRelative(5.4f, -3.1f, 12.4f, -11.3f, 12.4f, -14.4f)
            curveToRelative(0.0f, -0.9f, 0.7f, -3.1f, 1.6f, -4.8f)
            curveToRelative(2.7f, -5.2f, 3.6f, -15.0f, 2.0f, -22.1f)
            curveToRelative(-3.0f, -13.0f, -6.6f, -23.0f, -9.9f, -27.2f)
            curveToRelative(-0.4f, -0.5f, -1.4f, -1.9f, -2.2f, -3.0f)
            curveToRelative(-1.3f, -1.8f, -1.4f, -1.3f, -1.5f, 5.3f)
            curveToRelative(0.0f, 8.5f, -1.2f, 8.7f, -3.6f, 0.4f)
            curveToRelative(-1.9f, -6.6f, -4.5f, -12.2f, -5.6f, -12.2f)
            curveToRelative(-0.5f, -0.0f, -0.8f, -0.7f, -0.8f, -1.7f)
            curveToRelative(0.0f, -0.9f, -0.7f, -2.3f, -1.5f, -3.2f)
            curveToRelative(-0.8f, -0.9f, -2.9f, -3.3f, -4.7f, -5.4f)
            curveToRelative(-1.8f, -2.0f, -3.6f, -3.7f, -4.0f, -3.7f)
            curveToRelative(-0.5f, -0.0f, -0.8f, 2.6f, -0.8f, 5.8f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(71.7f, 280.7f)
            curveToRelative(-0.9f, 0.8f, -0.9f, 13.6f, -0.1f, 15.7f)
            curveToRelative(0.6f, 1.4f, 8.6f, 1.6f, 80.5f, 1.6f)
            lineToRelative(79.9f, -0.0f)
            lineToRelative(0.0f, -9.0f)
            lineToRelative(0.0f, -9.0f)
            lineToRelative(-79.8f, -0.0f)
            curveToRelative(-43.9f, -0.0f, -80.2f, 0.3f, -80.5f, 0.7f)
            close()
        }
    }
        .build()