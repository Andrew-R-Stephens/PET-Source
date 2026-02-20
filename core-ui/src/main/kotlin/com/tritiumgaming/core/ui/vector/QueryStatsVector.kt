package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.vector.color.IconVectorColors

fun getQueryStatsVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "QueryStatsVector",
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
            moveToRelative(105.0f, 561.0f)
            lineToRelative(-65.0f, -47.0f)
            lineToRelative(200.0f, -320.0f)
            lineToRelative(120.0f, 140.0f)
            lineToRelative(160.0f, -260.0f)
            lineToRelative(120.0f, 180.0f)
            lineToRelative(135.0f, -214.0f)
            lineToRelative(65.0f, 47.0f)
            lineToRelative(-198.0f, 314.0f)
            lineToRelative(-119.0f, -179.0f)
            lineToRelative(-152.0f, 247.0f)
            lineToRelative(-121.0f, -141.0f)
            close()
            moveTo(580.0f, 720.0f)
            quadToRelative(42.0f, 0.0f, 71.0f, -29.0f)
            reflectiveQuadToRelative(29.0f, -71.0f)
            reflectiveQuadToRelative(-29.0f, -71.0f)
            reflectiveQuadToRelative(-71.0f, -29.0f)
            reflectiveQuadToRelative(-71.0f, 29.0f)
            reflectiveQuadToRelative(-29.0f, 71.0f)
            reflectiveQuadToRelative(29.0f, 71.0f)
            reflectiveQuadToRelative(71.0f, 29.0f)
            moveTo(784.0f, 880.0f)
            lineTo(676.0f, 772.0f)
            quadToRelative(-21.0f, 14.0f, -45.5f, 21.0f)
            reflectiveQuadToRelative(-50.5f, 7.0f)
            quadToRelative(-75.0f, 0.0f, -127.5f, -52.5f)
            reflectiveQuadTo(400.0f, 620.0f)
            reflectiveQuadToRelative(52.5f, -127.5f)
            reflectiveQuadTo(580.0f, 440.0f)
            reflectiveQuadToRelative(127.5f, 52.5f)
            reflectiveQuadTo(760.0f, 620.0f)
            quadToRelative(0.0f, 26.0f, -7.0f, 50.5f)
            reflectiveQuadTo(732.0f, 716.0f)
            lineToRelative(108.0f, 108.0f)
            close()
        }
    }
    .build()