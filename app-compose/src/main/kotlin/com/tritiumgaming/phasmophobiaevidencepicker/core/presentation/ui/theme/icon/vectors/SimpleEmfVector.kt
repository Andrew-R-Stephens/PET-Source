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

fun getSimpleEmfVector(groupColors: List<Color>): ImageVector =
    Builder(
        name = "IconShEmf",
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
            moveTo(185.1f, 50.0f)
            curveToRelative(-1.9f, 4.4f, -3.8f, 8.0f, -4.3f, 8.0f)
            curveToRelative(-0.4f, -0.0f, -0.8f, 0.6f, -0.8f, 1.2f)
            curveToRelative(0.0f, 0.7f, -1.4f, 4.0f, -3.0f, 7.3f)
            curveToRelative(-1.6f, 3.3f, -3.0f, 6.6f, -3.0f, 7.3f)
            curveToRelative(0.0f, 0.7f, -0.7f, 1.5f, -1.5f, 1.8f)
            curveToRelative(-0.8f, 0.4f, -1.5f, 1.0f, -1.5f, 1.6f)
            curveToRelative(0.0f, 0.9f, -2.9f, 7.8f, -5.2f, 12.5f)
            curveToRelative(-0.6f, 1.3f, -1.5f, 2.3f, -2.0f, 2.3f)
            curveToRelative(-0.4f, -0.0f, -0.8f, 0.6f, -0.8f, 1.4f)
            curveToRelative(0.0f, 1.6f, -5.9f, 14.9f, -7.0f, 15.6f)
            curveToRelative(-0.4f, 0.3f, -2.4f, 4.3f, -4.5f, 9.0f)
            curveToRelative(-2.0f, 4.7f, -4.0f, 8.7f, -4.3f, 9.0f)
            curveToRelative(-0.7f, 0.5f, -4.0f, 7.9f, -6.2f, 13.7f)
            curveToRelative(-0.7f, 1.8f, -1.6f, 3.3f, -2.1f, 3.3f)
            curveToRelative(-0.5f, -0.0f, -0.9f, 0.4f, -0.9f, 1.0f)
            curveToRelative(0.0f, 1.1f, -6.2f, 14.3f, -7.0f, 15.0f)
            curveToRelative(-1.0f, 0.7f, -6.0f, 12.1f, -6.0f, 13.4f)
            curveToRelative(0.0f, 0.6f, -0.9f, 1.9f, -2.0f, 2.9f)
            curveToRelative(-1.1f, 0.9f, -2.0f, 2.3f, -2.0f, 3.0f)
            curveToRelative(0.0f, 0.7f, -1.3f, 4.1f, -3.0f, 7.5f)
            curveToRelative(-1.7f, 3.4f, -3.0f, 6.6f, -3.0f, 7.1f)
            curveToRelative(0.0f, 0.5f, -0.5f, 1.1f, -1.2f, 1.3f)
            curveToRelative(-1.7f, 0.6f, -3.8f, 8.6f, -3.8f, 14.6f)
            curveToRelative(0.0f, 5.5f, 2.2f, 12.2f, 3.9f, 12.2f)
            curveToRelative(0.6f, -0.0f, 1.1f, 0.6f, 1.1f, 1.3f)
            curveToRelative(0.0f, 2.2f, 7.8f, 8.5f, 11.8f, 9.7f)
            curveToRelative(6.9f, 1.9f, 15.8f, 1.0f, 18.2f, -1.9f)
            curveToRelative(0.3f, -0.3f, 1.6f, -1.2f, 3.0f, -1.9f)
            curveToRelative(1.4f, -0.7f, 3.6f, -2.9f, 5.0f, -5.0f)
            curveToRelative(1.4f, -2.0f, 2.8f, -3.9f, 3.2f, -4.2f)
            curveToRelative(0.4f, -0.3f, 2.2f, -7.8f, 4.0f, -16.5f)
            curveToRelative(1.8f, -8.8f, 3.6f, -16.2f, 3.9f, -16.5f)
            curveToRelative(0.4f, -0.3f, 2.1f, -7.7f, 3.9f, -16.5f)
            curveToRelative(1.8f, -8.8f, 3.6f, -16.2f, 3.9f, -16.5f)
            curveToRelative(0.4f, -0.3f, 1.7f, -5.9f, 3.0f, -12.5f)
            curveToRelative(1.2f, -6.6f, 2.6f, -13.6f, 3.1f, -15.5f)
            curveToRelative(0.4f, -1.9f, 1.2f, -5.5f, 1.5f, -8.0f)
            curveToRelative(0.4f, -2.5f, 1.1f, -4.8f, 1.6f, -5.1f)
            curveToRelative(0.5f, -0.3f, 1.5f, -3.9f, 2.3f, -8.0f)
            curveToRelative(0.8f, -4.1f, 2.1f, -11.0f, 3.0f, -15.4f)
            curveToRelative(0.8f, -4.4f, 2.0f, -8.1f, 2.6f, -8.3f)
            curveToRelative(0.5f, -0.2f, 1.0f, -1.3f, 1.0f, -2.5f)
            curveToRelative(0.0f, -1.2f, 1.4f, -9.2f, 3.2f, -17.7f)
            curveToRelative(1.7f, -8.6f, 2.9f, -16.3f, 2.5f, -17.3f)
            curveToRelative(-0.4f, -1.1f, -1.7f, -1.7f, -3.9f, -1.7f)
            curveToRelative(-3.2f, -0.0f, -3.4f, 0.3f, -6.7f, 8.0f)
            close()
            moveTo(142.5f, 201.5f)
            curveToRelative(1.9f, 2.0f, 2.5f, 3.6f, 2.5f, 6.8f)
            curveToRelative(0.0f, 6.0f, -1.9f, 9.0f, -6.5f, 10.5f)
            curveToRelative(-8.1f, 2.7f, -14.1f, -1.6f, -13.9f, -9.9f)
            curveToRelative(0.1f, -7.1f, 2.9f, -9.9f, 10.0f, -9.9f)
            curveToRelative(4.4f, -0.0f, 5.8f, 0.5f, 7.9f, 2.5f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(144.7f, 64.6f)
            curveToRelative(-0.3f, 0.5f, -0.8f, 8.6f, -1.2f, 18.0f)
            lineToRelative(-0.7f, 17.1f)
            lineToRelative(4.7f, 0.7f)
            curveToRelative(3.9f, 0.6f, 4.9f, 0.4f, 6.1f, -1.1f)
            curveToRelative(0.7f, -1.0f, 1.4f, -2.6f, 1.4f, -3.6f)
            curveToRelative(0.0f, -0.9f, 0.4f, -1.7f, 0.9f, -1.7f)
            curveToRelative(0.4f, -0.0f, 2.3f, -3.4f, 4.1f, -7.5f)
            curveToRelative(1.8f, -4.1f, 3.6f, -7.5f, 3.9f, -7.5f)
            curveToRelative(0.4f, -0.0f, 1.7f, -2.7f, 3.0f, -6.1f)
            curveToRelative(2.9f, -7.2f, 2.4f, -7.9f, -5.9f, -7.9f)
            curveToRelative(-3.0f, -0.0f, -7.8f, -0.3f, -10.6f, -0.6f)
            curveToRelative(-2.8f, -0.4f, -5.4f, -0.3f, -5.7f, 0.2f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(120.5f, 66.1f)
            curveToRelative(-2.2f, 0.5f, -6.7f, 1.4f, -10.0f, 1.9f)
            curveToRelative(-3.3f, 0.6f, -6.2f, 1.6f, -6.6f, 2.2f)
            curveToRelative(-0.4f, 0.5f, -2.4f, 1.4f, -4.5f, 1.8f)
            curveToRelative(-5.0f, 1.0f, -14.6f, 4.7f, -15.3f, 5.9f)
            curveToRelative(-0.7f, 1.0f, -9.5f, 5.1f, -11.0f, 5.1f)
            curveToRelative(-0.5f, -0.0f, -2.2f, 1.3f, -3.8f, 2.9f)
            curveToRelative(-1.6f, 1.5f, -4.3f, 3.6f, -6.1f, 4.5f)
            curveToRelative(-1.8f, 0.9f, -3.2f, 1.8f, -3.2f, 2.1f)
            curveToRelative(0.0f, 1.1f, 3.5f, 7.5f, 4.1f, 7.5f)
            curveToRelative(0.8f, -0.0f, 7.9f, 11.7f, 7.9f, 13.1f)
            curveToRelative(0.0f, 1.4f, 4.7f, 1.1f, 6.2f, -0.4f)
            curveToRelative(1.5f, -1.5f, 7.2f, -3.9f, 14.8f, -6.4f)
            curveToRelative(3.0f, -1.0f, 5.7f, -2.1f, 6.0f, -2.4f)
            curveToRelative(0.8f, -1.1f, 16.0f, -3.8f, 25.3f, -4.5f)
            curveToRelative(5.8f, -0.4f, 9.0f, -1.1f, 9.4f, -1.9f)
            curveToRelative(0.9f, -2.6f, -1.7f, -30.9f, -3.0f, -31.7f)
            curveToRelative(-1.5f, -0.9f, -4.9f, -0.8f, -10.2f, 0.3f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(195.1f, 80.2f)
            curveToRelative(-0.7f, 3.5f, -1.8f, 9.0f, -2.6f, 12.3f)
            curveToRelative(-0.7f, 3.3f, -1.6f, 8.0f, -2.0f, 10.5f)
            curveToRelative(-0.4f, 2.5f, -1.2f, 4.8f, -1.7f, 5.1f)
            curveToRelative(-0.5f, 0.3f, -1.1f, 2.2f, -1.4f, 4.1f)
            curveToRelative(-0.6f, 3.4f, -0.4f, 3.7f, 3.1f, 5.4f)
            curveToRelative(2.0f, 0.9f, 3.9f, 2.3f, 4.2f, 3.1f)
            curveToRelative(1.0f, 2.5f, 2.8f, 1.3f, 6.3f, -4.2f)
            curveToRelative(1.9f, -3.0f, 4.5f, -6.7f, 5.7f, -8.3f)
            curveToRelative(1.3f, -1.5f, 2.3f, -3.1f, 2.3f, -3.5f)
            curveToRelative(0.0f, -0.9f, 3.9f, -6.7f, 5.6f, -8.2f)
            curveToRelative(2.1f, -1.9f, 5.4f, -8.3f, 5.4f, -10.4f)
            curveToRelative(0.0f, -1.6f, -1.4f, -2.8f, -5.7f, -4.9f)
            curveToRelative(-3.2f, -1.5f, -6.0f, -3.0f, -6.3f, -3.3f)
            curveToRelative(-0.7f, -0.8f, -8.8f, -3.9f, -10.3f, -3.9f)
            curveToRelative(-0.8f, -0.0f, -1.8f, 2.5f, -2.6f, 6.2f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(231.8f, 93.8f)
            curveToRelative(-1.0f, 0.2f, -1.8f, 0.7f, -1.8f, 1.2f)
            curveToRelative(0.0f, 0.5f, -1.3f, 2.5f, -3.0f, 4.3f)
            curveToRelative(-1.6f, 1.9f, -3.0f, 3.8f, -3.0f, 4.1f)
            curveToRelative(0.0f, 0.4f, -1.5f, 2.2f, -3.2f, 4.1f)
            curveToRelative(-5.7f, 5.8f, -11.3f, 12.6f, -12.0f, 14.5f)
            curveToRelative(-0.4f, 1.0f, -1.6f, 2.4f, -2.7f, 3.0f)
            curveToRelative(-3.0f, 1.6f, -2.6f, 3.6f, 1.7f, 7.9f)
            curveToRelative(4.7f, 4.8f, 9.5f, 10.8f, 10.4f, 12.9f)
            curveToRelative(0.3f, 1.0f, 1.8f, 3.0f, 3.3f, 4.7f)
            curveToRelative(2.7f, 3.0f, 5.1f, 8.0f, 7.5f, 15.2f)
            curveToRelative(0.7f, 2.4f, 1.6f, 4.3f, 2.0f, 4.3f)
            curveToRelative(0.4f, -0.0f, 1.0f, 1.3f, 1.4f, 3.0f)
            curveToRelative(0.3f, 1.7f, 1.2f, 3.0f, 2.0f, 3.0f)
            curveToRelative(2.1f, -0.0f, 11.0f, -3.1f, 11.6f, -4.0f)
            curveToRelative(0.3f, -0.4f, 5.5f, -2.5f, 11.5f, -4.6f)
            curveToRelative(6.1f, -2.0f, 11.2f, -4.1f, 11.5f, -4.5f)
            curveToRelative(0.3f, -0.4f, 3.5f, -1.8f, 7.0f, -3.1f)
            curveToRelative(6.8f, -2.4f, 8.7f, -5.0f, 5.7f, -7.9f)
            curveToRelative(-0.7f, -0.8f, -3.0f, -4.7f, -5.1f, -8.7f)
            curveToRelative(-2.0f, -4.1f, -4.6f, -8.3f, -5.6f, -9.5f)
            curveToRelative(-1.1f, -1.1f, -2.3f, -3.0f, -2.6f, -4.1f)
            curveToRelative(-0.4f, -1.2f, -2.0f, -3.3f, -3.5f, -4.7f)
            curveToRelative(-1.6f, -1.4f, -2.9f, -3.1f, -2.9f, -3.7f)
            curveToRelative(0.0f, -1.5f, -21.2f, -23.2f, -22.7f, -23.2f)
            curveToRelative(-0.5f, -0.0f, -2.1f, -1.0f, -3.4f, -2.3f)
            curveToRelative(-1.3f, -1.3f, -3.2f, -2.1f, -4.1f, -1.9f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(48.0f, 102.6f)
            curveToRelative(-0.8f, 0.3f, -2.5f, 1.9f, -3.7f, 3.7f)
            curveToRelative(-1.2f, 1.8f, -3.9f, 5.0f, -6.0f, 7.2f)
            curveToRelative(-5.3f, 5.6f, -9.4f, 10.9f, -12.2f, 15.8f)
            curveToRelative(-1.3f, 2.3f, -2.7f, 4.4f, -3.1f, 4.7f)
            curveToRelative(-0.4f, 0.3f, -2.5f, 4.5f, -4.6f, 9.5f)
            curveToRelative(-2.1f, 4.9f, -4.1f, 9.2f, -4.4f, 9.5f)
            curveToRelative(-1.3f, 1.2f, -3.0f, 6.3f, -3.0f, 8.9f)
            curveToRelative(0.0f, 2.5f, 0.6f, 3.1f, 5.6f, 5.0f)
            curveToRelative(8.0f, 3.0f, 8.9f, 2.8f, 11.5f, -2.7f)
            curveToRelative(1.3f, -2.6f, 2.9f, -5.3f, 3.6f, -6.0f)
            curveToRelative(0.7f, -0.7f, 1.3f, -2.0f, 1.3f, -2.8f)
            curveToRelative(0.0f, -0.8f, 0.4f, -1.4f, 0.9f, -1.4f)
            curveToRelative(0.5f, -0.0f, 1.2f, -0.8f, 1.5f, -1.8f)
            curveToRelative(0.4f, -1.0f, 1.4f, -2.7f, 2.4f, -3.8f)
            curveToRelative(0.9f, -1.0f, 3.0f, -3.7f, 4.7f, -5.9f)
            curveToRelative(3.3f, -4.4f, 13.0f, -14.1f, 17.8f, -17.7f)
            curveToRelative(6.4f, -4.9f, 6.5f, -5.5f, 2.0f, -9.1f)
            curveToRelative(-1.3f, -1.0f, -2.3f, -2.5f, -2.3f, -3.2f)
            curveToRelative(0.0f, -0.8f, -1.4f, -2.5f, -3.1f, -3.7f)
            curveToRelative(-1.7f, -1.3f, -3.5f, -3.3f, -4.0f, -4.6f)
            curveToRelative(-0.8f, -2.1f, -1.7f, -2.4f, -4.9f, -1.6f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(282.0f, 170.7f)
            curveToRelative(-1.4f, 0.3f, -2.7f, 0.9f, -3.0f, 1.3f)
            curveToRelative(-0.5f, 0.8f, -6.6f, 2.5f, -20.0f, 5.6f)
            curveToRelative(-3.5f, 0.8f, -6.7f, 1.8f, -7.0f, 2.2f)
            curveToRelative(-0.3f, 0.5f, -3.9f, 1.7f, -8.0f, 2.8f)
            lineToRelative(-7.5f, 1.9f)
            lineToRelative(-0.1f, 21.5f)
            curveToRelative(-0.1f, 11.8f, -0.6f, 23.1f, -1.1f, 25.1f)
            lineToRelative(-1.0f, 3.7f)
            lineToRelative(4.6f, 0.5f)
            curveToRelative(3.8f, 0.4f, 51.4f, 0.2f, 57.4f, -0.2f)
            curveToRelative(1.5f, -0.1f, 1.7f, -1.6f, 1.7f, -16.1f)
            curveToRelative(0.0f, -8.8f, -0.4f, -16.0f, -0.9f, -16.0f)
            curveToRelative(-0.5f, -0.0f, -1.4f, -3.7f, -2.1f, -8.3f)
            curveToRelative(-1.8f, -11.8f, -4.7f, -22.9f, -6.2f, -23.8f)
            curveToRelative(-1.4f, -0.9f, -2.7f, -0.9f, -6.8f, -0.2f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(7.0f, 177.4f)
            curveToRelative(0.0f, 0.8f, -0.5f, 1.6f, -1.0f, 1.8f)
            curveToRelative(-2.6f, 0.8f, -3.6f, 35.3f, -1.4f, 51.0f)
            curveToRelative(0.7f, 4.7f, 0.8f, 4.8f, 4.1f, 4.8f)
            curveToRelative(1.9f, -0.0f, 4.1f, -0.7f, 4.9f, -1.5f)
            curveToRelative(0.9f, -0.8f, 1.1f, -1.5f, 0.5f, -1.5f)
            curveToRelative(-0.7f, -0.0f, -1.1f, -3.0f, -1.1f, -8.0f)
            curveToRelative(0.0f, -4.7f, 0.4f, -8.0f, 1.0f, -8.0f)
            curveToRelative(0.6f, -0.0f, 1.0f, -1.9f, 1.0f, -4.3f)
            curveToRelative(0.0f, -6.0f, 3.8f, -25.9f, 5.4f, -28.6f)
            curveToRelative(1.3f, -2.1f, -1.4f, -4.9f, -5.8f, -6.0f)
            curveToRelative(-5.9f, -1.5f, -7.6f, -1.4f, -7.6f, 0.3f)
            close()
        }
    }
        .build()