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


fun getResetCrosshairsVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "ResetCrosshairsVector",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 128.0f,
        viewportHeight = 128.0f,
    )
    .apply {
        path(
            fill = SolidColor(Color(0xFFFEFEFE)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero,
        ) {
            moveTo(69.0f, 4.0f)
            horizontalLineTo(59.0f)
            verticalLineToRelative(11.0f)
            curveToRelative(-10.01f, 0.0f, -19.87f, 3.66f, -27.0f, 10.75f)
            lineToRelative(-2.05f, 2.43f)
            curveToRelative(-0.74f, 0.9f, -1.47f, 1.79f, -1.96f, 2.32f)
            verticalLineTo(19.0f)
            horizontalLineTo(18.0f)
            curveToRelative(-0.07f, 2.14f, 0.15f, 26.9f, 0.0f, 29.0f)
            horizontalLineToRelative(30.0f)
            verticalLineTo(38.0f)
            horizontalLineTo(35.0f)
            lineToRelative(1.09f, -1.63f)
            lineTo(37.0f, 35.0f)
            curveToRelative(12.25f, -9.31f, 25.2f, -11.51f, 39.84f, -7.91f)
            curveTo(86.16f, 29.78f, 92.07f, 34.52f, 97.0f, 43.0f)
            curveToRelative(4.94f, 9.81f, 7.74f, 19.83f, 4.79f, 30.71f)
            curveTo(98.0f, 84.67f, 93.54f, 92.63f, 83.0f, 98.0f)
            curveToRelative(-10.79f, 5.14f, -19.67f, 6.96f, -31.13f, 3.2f)
            curveToRelative(-11.13f, -4.01f, -17.44f, -9.04f, -22.63f, -19.86f)
            curveToRelative(-3.35f, -7.46f, -4.73f, -14.27f, -4.1f, -21.33f)
            horizontalLineTo(4.0f)
            verticalLineToRelative(9.0f)
            horizontalLineToRelative(11.0f)
            curveToRelative(0.0f, 10.21f, 4.01f, 19.55f, 10.97f, 26.96f)
            lineTo(28.0f, 98.0f)
            curveToRelative(7.8f, 8.05f, 15.9f, 13.48f, 27.25f, 14.81f)
            lineTo(59.0f, 113.0f)
            verticalLineToRelative(11.0f)
            horizontalLineToRelative(10.0f)
            verticalLineToRelative(-11.0f)
            curveToRelative(8.84f, 0.0f, 16.6f, -3.3f, 24.0f, -8.0f)
            curveToRelative(10.13f, -8.23f, 18.27f, -18.86f, 19.75f, -32.13f)
            lineTo(113.0f, 69.0f)
            horizontalLineToRelative(11.0f)
            verticalLineToRelative(-9.0f)
            horizontalLineToRelative(-11.0f)
            curveToRelative(0.0f, -9.58f, -3.14f, -19.02f, -9.26f, -26.55f)
            curveToRelative(-8.16f, -9.08f, -17.83f, -16.95f, -30.31f, -18.2f)
            lineTo(69.0f, 15.0f)
            close()
        }
    }
    .build()