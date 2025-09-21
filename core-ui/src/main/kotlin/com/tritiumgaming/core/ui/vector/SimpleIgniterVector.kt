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
import com.tritiumgaming.core.ui.icon.color.IconVectorColors

fun getSimpleIgniterVector(colors: IconVectorColors = IconVectorColors.defaults()): ImageVector =
    Builder(
        name = "IconShIgniter",
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
            moveTo(150.0f, 8.0f)
            curveToRelative(-2.9f, 2.7f, -5.6f, 5.0f, -6.0f, 5.0f)
            curveToRelative(-0.5f, -0.0f, -2.5f, 2.3f, -4.5f, 5.2f)
            curveToRelative(-2.1f, 2.8f, -4.9f, 6.1f, -6.4f, 7.4f)
            curveToRelative(-1.5f, 1.2f, -4.0f, 4.8f, -5.6f, 7.9f)
            curveToRelative(-1.7f, 3.1f, -3.9f, 6.6f, -5.0f, 7.8f)
            curveToRelative(-2.2f, 2.4f, -6.6f, 11.3f, -10.1f, 20.7f)
            curveToRelative(-1.4f, 3.5f, -2.8f, 6.7f, -3.3f, 7.0f)
            curveToRelative(-1.9f, 1.2f, -7.9f, 23.5f, -10.7f, 40.0f)
            curveToRelative(-0.9f, 5.2f, -1.9f, 9.7f, -2.4f, 10.0f)
            curveToRelative(-0.4f, 0.3f, -1.3f, 5.9f, -2.0f, 12.5f)
            curveToRelative(-0.6f, 6.6f, -1.3f, 12.1f, -1.4f, 12.3f)
            curveToRelative(-0.5f, 0.4f, -5.3f, -7.1f, -7.2f, -11.1f)
            curveToRelative(-1.0f, -2.0f, -2.1f, -3.7f, -2.5f, -3.7f)
            curveToRelative(-0.4f, -0.0f, -1.7f, -2.6f, -3.0f, -5.8f)
            curveToRelative(-1.3f, -3.1f, -2.9f, -6.8f, -3.5f, -8.1f)
            curveToRelative(-1.2f, -2.3f, -1.3f, -2.3f, -3.5f, 2.0f)
            curveToRelative(-1.2f, 2.4f, -2.5f, 4.6f, -2.9f, 4.9f)
            curveToRelative(-1.0f, 0.7f, -5.3f, 12.1f, -9.1f, 24.0f)
            curveToRelative(-1.8f, 5.8f, -3.6f, 10.7f, -4.0f, 11.0f)
            curveToRelative(-2.1f, 1.5f, -4.3f, 19.4f, -4.3f, 35.0f)
            curveToRelative(-0.1f, 16.4f, 2.1f, 33.0f, 4.5f, 33.8f)
            curveToRelative(0.5f, 0.2f, 0.9f, 1.0f, 0.9f, 1.8f)
            curveToRelative(0.0f, 0.8f, 0.4f, 2.2f, 1.0f, 3.2f)
            curveToRelative(0.5f, 0.9f, 2.3f, 5.2f, 4.0f, 9.4f)
            curveToRelative(1.7f, 4.2f, 4.2f, 8.8f, 5.6f, 10.3f)
            curveToRelative(1.3f, 1.4f, 2.4f, 2.9f, 2.4f, 3.2f)
            curveToRelative(0.0f, 0.9f, 11.1f, 15.2f, 12.0f, 15.5f)
            curveToRelative(0.5f, 0.2f, 3.4f, 2.8f, 6.6f, 5.8f)
            curveToRelative(5.4f, 5.2f, 10.2f, 8.5f, 11.1f, 7.6f)
            curveToRelative(0.2f, -0.2f, -0.5f, -4.2f, -1.6f, -8.9f)
            curveToRelative(-1.2f, -4.6f, -2.1f, -9.6f, -2.1f, -11.0f)
            curveToRelative(0.0f, -1.4f, -0.6f, -2.7f, -1.2f, -2.9f)
            curveToRelative(-1.7f, -0.6f, -1.8f, -32.5f, -0.2f, -33.6f)
            curveToRelative(0.6f, -0.4f, 1.5f, -1.8f, 1.9f, -3.2f)
            curveToRelative(1.0f, -3.3f, 6.4f, -12.1f, 8.4f, -13.8f)
            curveToRelative(2.6f, -2.1f, 10.3f, -5.3f, 14.9f, -6.2f)
            curveToRelative(4.2f, -0.7f, 4.3f, -0.7f, 3.7f, 1.4f)
            curveToRelative(-0.3f, 1.1f, -0.9f, 7.1f, -1.2f, 13.2f)
            curveToRelative(-0.7f, 12.8f, 0.2f, 16.2f, 5.2f, 21.6f)
            curveToRelative(3.3f, 3.7f, 3.6f, 3.8f, 10.4f, 3.8f)
            curveToRelative(8.2f, -0.0f, 11.3f, -1.0f, 14.7f, -4.7f)
            curveToRelative(4.0f, -4.6f, 5.3f, -7.8f, 6.0f, -15.0f)
            curveToRelative(0.8f, -8.9f, -1.6f, -21.4f, -6.7f, -35.1f)
            curveToRelative(-2.2f, -5.6f, -3.9f, -11.1f, -3.9f, -12.1f)
            curveToRelative(0.0f, -1.1f, -0.6f, -2.1f, -1.2f, -2.3f)
            curveToRelative(-1.8f, -0.6f, -1.8f, -18.6f, -0.1f, -19.6f)
            curveToRelative(0.7f, -0.4f, 1.9f, -2.4f, 2.7f, -4.3f)
            curveToRelative(0.8f, -2.0f, 2.2f, -4.1f, 3.0f, -4.9f)
            curveToRelative(1.4f, -1.1f, 1.5f, -0.9f, 1.0f, 1.8f)
            curveToRelative(-1.3f, 6.5f, 3.6f, 24.4f, 7.2f, 26.5f)
            curveToRelative(0.8f, 0.4f, 1.4f, 1.3f, 1.4f, 2.0f)
            curveToRelative(0.0f, 1.6f, 8.6f, 14.2f, 12.7f, 18.6f)
            curveToRelative(3.6f, 3.9f, 10.3f, 16.9f, 10.3f, 20.0f)
            curveToRelative(0.0f, 1.0f, 0.5f, 2.1f, 1.0f, 2.3f)
            curveToRelative(1.2f, 0.4f, 3.9f, 14.1f, 4.0f, 19.5f)
            curveToRelative(0.0f, 1.8f, 0.4f, 3.3f, 1.0f, 3.3f)
            curveToRelative(1.2f, -0.0f, 5.5f, -8.5f, 6.5f, -12.7f)
            curveToRelative(0.3f, -1.8f, 1.0f, -3.3f, 1.5f, -3.3f)
            curveToRelative(0.8f, -0.0f, 3.7f, -11.6f, 5.6f, -22.5f)
            lineToRelative(0.7f, -4.0f)
            lineToRelative(0.8f, 4.5f)
            curveToRelative(4.5f, 25.4f, 3.5f, 41.5f, -3.7f, 62.0f)
            curveToRelative(-1.4f, 4.1f, -2.9f, 7.7f, -3.4f, 8.0f)
            curveToRelative(-0.4f, 0.3f, -2.2f, 3.1f, -4.0f, 6.3f)
            curveToRelative(-1.8f, 3.2f, -6.1f, 8.8f, -9.6f, 12.4f)
            curveToRelative(-3.5f, 3.7f, -6.2f, 6.8f, -6.0f, 7.0f)
            curveToRelative(0.6f, 0.7f, 8.5f, -3.8f, 11.7f, -6.6f)
            curveToRelative(1.6f, -1.4f, 4.9f, -3.7f, 7.4f, -5.1f)
            curveToRelative(10.7f, -6.1f, 30.2f, -26.0f, 35.7f, -36.5f)
            curveToRelative(1.7f, -3.3f, 3.4f, -6.2f, 3.8f, -6.5f)
            curveToRelative(2.0f, -1.4f, 6.0f, -14.6f, 7.1f, -23.8f)
            curveToRelative(1.0f, -8.1f, 1.1f, -12.5f, 0.1f, -21.1f)
            curveToRelative(-0.7f, -6.0f, -1.6f, -12.1f, -2.2f, -13.7f)
            curveToRelative(-0.5f, -1.6f, -1.4f, -4.8f, -2.0f, -7.1f)
            curveToRelative(-0.6f, -2.3f, -1.6f, -4.8f, -2.4f, -5.5f)
            curveToRelative(-0.7f, -0.7f, -2.4f, -4.3f, -3.9f, -7.8f)
            curveToRelative(-4.6f, -11.6f, -6.8f, -16.1f, -8.8f, -18.1f)
            curveToRelative(-1.1f, -1.1f, -3.3f, -4.4f, -4.9f, -7.4f)
            curveToRelative(-4.1f, -7.6f, -8.1f, -13.5f, -9.1f, -13.5f)
            curveToRelative(-0.5f, -0.0f, -0.9f, -0.5f, -0.9f, -1.1f)
            curveToRelative(0.0f, -0.6f, -1.3f, -2.9f, -2.9f, -5.3f)
            curveToRelative(-1.6f, -2.3f, -4.1f, -6.4f, -5.7f, -9.1f)
            curveToRelative(-1.5f, -2.8f, -3.3f, -5.5f, -4.0f, -6.0f)
            curveToRelative(-0.6f, -0.6f, -3.6f, -6.2f, -6.5f, -12.5f)
            curveToRelative(-2.9f, -6.3f, -5.6f, -11.8f, -6.2f, -12.1f)
            curveToRelative(-1.5f, -1.0f, -5.6f, -16.9f, -6.3f, -24.4f)
            curveToRelative(-0.6f, -6.1f, -2.3f, -12.4f, -2.4f, -8.8f)
            curveToRelative(0.0f, 0.7f, -1.8f, 3.2f, -4.0f, 5.5f)
            curveToRelative(-2.2f, 2.4f, -4.9f, 6.2f, -6.0f, 8.5f)
            curveToRelative(-2.3f, 4.9f, -6.0f, 17.8f, -6.0f, 20.9f)
            curveToRelative(0.0f, 1.2f, -0.6f, 2.4f, -1.4f, 2.7f)
            curveToRelative(-1.0f, 0.4f, -2.0f, -1.8f, -3.9f, -8.1f)
            curveToRelative(-4.9f, -16.9f, -6.1f, -24.1f, -6.0f, -37.7f)
            curveToRelative(0.0f, -12.5f, 1.5f, -20.6f, 5.8f, -31.8f)
            curveToRelative(1.2f, -3.0f, -1.1f, -1.9f, -6.5f, 3.3f)
            close()
        }
    }
        .build()