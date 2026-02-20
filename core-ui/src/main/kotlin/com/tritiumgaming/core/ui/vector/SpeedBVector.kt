package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.vector.color.IconVectorColors

fun getSpeedBVector(
    colors: IconVectorColors
): ImageVector = Builder(
    name = "SpeedBVector",
    defaultWidth = 200.0.dp,
    defaultHeight = 200.0.dp,
    viewportWidth = 177.0f,
    viewportHeight = 177.0f,
)
    .apply {
        group {
            path(
                fill = SolidColor(colors.fillColor),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(125.0f, 37.0f)
                verticalLineToRelative(26.0f)
                horizontalLineToRelative(-10.0f)
                lineToRelative(-0.66f, -2.16f)
                curveToRelative(-2.31f, -6.97f, -4.6f, -13.26f, -11.34f, -16.93f)
                curveToRelative(-6.69f, -2.59f, -13.91f, -3.2f, -20.78f, -1.03f)
                curveToRelative(-4.43f, 1.97f, -8.54f, 4.17f, -10.66f, 8.77f)
                curveToRelative(-1.62f, 6.86f, -1.93f, 15.19f, 0.82f, 21.78f)
                curveToRelative(5.85f, 9.24f, 16.41f, 13.8f, 25.87f, 18.44f)
                curveTo(109.42f, 97.45f, 121.42f, 105.86f, 126.0f, 118.0f)
                curveToRelative(2.35f, 11.86f, 1.61f, 22.28f, -4.63f, 32.69f)
                curveToRelative(-5.45f, 7.74f, -13.89f, 11.95f, -23.01f, 13.87f)
                curveTo(84.11f, 166.44f, 65.14f, 166.57f, 52.0f, 160.0f)
                verticalLineToRelative(-26.0f)
                horizontalLineToRelative(9.0f)
                lineToRelative(0.81f, 2.6f)
                curveToRelative(3.1f, 9.05f, 6.4f, 14.38f, 14.94f, 18.84f)
                curveToRelative(8.52f, 1.48f, 16.31f, 1.05f, 24.25f, -2.44f)
                lineToRelative(2.18f, -0.96f)
                curveToRelative(2.62f, -1.5f, 2.9f, -3.22f, 3.82f, -6.04f)
                lineToRelative(1.03f, -2.24f)
                curveToRelative(2.23f, -6.36f, 2.25f, -14.9f, -0.47f, -21.13f)
                curveToRelative(-5.86f, -9.44f, -15.71f, -14.15f, -25.17f, -19.24f)
                curveTo(71.39f, 97.46f, 59.41f, 90.85f, 54.0f, 79.0f)
                curveToRelative(-2.7f, -10.22f, -1.88f, -21.13f, 3.3f, -30.3f)
                curveToRelative(5.33f, -7.87f, 13.83f, -12.56f, 22.89f, -15.14f)
                curveTo(94.83f, 31.12f, 110.9f, 32.97f, 125.0f, 37.0f)
                moveTo(52.0f, 14.0f)
                horizontalLineToRelative(75.0f)
                verticalLineToRelative(11.0f)
                horizontalLineTo(52.0f)
                close()
            }
        }
    }
    .build()