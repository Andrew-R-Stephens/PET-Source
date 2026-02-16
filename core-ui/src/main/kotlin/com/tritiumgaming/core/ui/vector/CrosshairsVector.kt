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

fun getCrosshairsVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "CrosshairsVector",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f,
    )
    .apply {
        path(
            fill = SolidColor(colors.fillColor),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero,
        ) {
            moveTo(450.0f, 918.0f)
            verticalLineToRelative(-75.0f)
            quadToRelative(-137.0f, -14.0f, -228.0f, -105.0f)
            reflectiveQuadTo(117.0f, 510.0f)
            lineTo(42.0f, 510.0f)
            verticalLineToRelative(-60.0f)
            horizontalLineToRelative(75.0f)
            quadToRelative(14.0f, -137.0f, 105.0f, -228.0f)
            reflectiveQuadToRelative(228.0f, -105.0f)
            verticalLineToRelative(-75.0f)
            horizontalLineToRelative(60.0f)
            verticalLineToRelative(75.0f)
            quadToRelative(137.0f, 14.0f, 228.0f, 105.0f)
            reflectiveQuadToRelative(105.0f, 228.0f)
            horizontalLineToRelative(75.0f)
            verticalLineToRelative(60.0f)
            horizontalLineToRelative(-75.0f)
            quadToRelative(-14.0f, 137.0f, -105.0f, 228.0f)
            reflectiveQuadTo(510.0f, 843.0f)
            verticalLineToRelative(75.0f)
            close()
            moveTo(694.5f, 694.5f)
            quadTo(784.0f, 605.0f, 784.0f, 480.0f)
            reflectiveQuadToRelative(-89.5f, -214.5f)
            reflectiveQuadTo(480.0f, 176.0f)
            reflectiveQuadToRelative(-214.5f, 89.5f)
            reflectiveQuadTo(176.0f, 480.0f)
            reflectiveQuadToRelative(89.5f, 214.5f)
            reflectiveQuadTo(480.0f, 784.0f)
            reflectiveQuadToRelative(214.5f, -89.5f)
        }
    }
    .build()