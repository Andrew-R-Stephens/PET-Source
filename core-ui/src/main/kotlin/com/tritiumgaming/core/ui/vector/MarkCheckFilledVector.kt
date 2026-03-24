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
import com.tritiumgaming.core.ui.vector.color.IconVectorColors

fun getMarkCheckFilledVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "MarkCheckFilledVector",
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
            moveToRelative(421.0f, 662.0f)
            lineToRelative(283.0f, -283.0f)
            lineToRelative(-46.0f, -45.0f)
            lineToRelative(-237.0f, 237.0f)
            lineToRelative(-120.0f, -120.0f)
            lineToRelative(-45.0f, 45.0f)
            lineToRelative(165.0f, 166.0f)
            close()
            moveTo(480.0f, 880.0f)
            quadToRelative(-82.0f, 0.0f, -155.0f, -31.5f)
            reflectiveQuadToRelative(-127.5f, -86.0f)
            quadTo(143.0f, 708.0f, 111.5f, 635.0f)
            reflectiveQuadTo(80.0f, 480.0f)
            quadToRelative(0.0f, -83.0f, 31.5f, -156.0f)
            reflectiveQuadToRelative(86.0f, -127.0f)
            quadTo(252.0f, 143.0f, 325.0f, 111.5f)
            reflectiveQuadTo(480.0f, 80.0f)
            quadToRelative(83.0f, 0.0f, 156.0f, 31.5f)
            reflectiveQuadTo(763.0f, 197.0f)
            quadToRelative(54.0f, 54.0f, 85.5f, 127.0f)
            reflectiveQuadTo(880.0f, 480.0f)
            quadToRelative(0.0f, 82.0f, -31.5f, 155.0f)
            reflectiveQuadTo(763.0f, 762.5f)
            quadToRelative(-54.0f, 54.5f, -127.0f, 86.0f)
            reflectiveQuadTo(480.0f, 880.0f)
            close()
        }
    }
    .build()

