package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.color.IconVectorColors

fun getAnalyticsVector(
    colors: IconVectorColors
): ImageVector =
    Builder(
        name = "AnalyticsVector",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f,
    )
    .apply {
        path(
            fill = SolidColor(
                colors.fillColor
            ),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero,
        ) {
            moveTo(284.0f, 683.0f)
            horizontalLineToRelative(60.0f)
            verticalLineToRelative(-205.0f)
            horizontalLineToRelative(-60.0f)
            close()
            moveTo(616.0f, 683.0f)
            horizontalLineToRelative(60.0f)
            verticalLineToRelative(-420.0f)
            horizontalLineToRelative(-60.0f)
            close()
            moveTo(450.0f, 683.0f)
            horizontalLineToRelative(60.0f)
            verticalLineToRelative(-118.0f)
            horizontalLineToRelative(-60.0f)
            close()
            moveTo(450.0f, 478.0f)
            horizontalLineToRelative(60.0f)
            verticalLineToRelative(-60.0f)
            horizontalLineToRelative(-60.0f)
            close()
            moveTo(180.0f, 840.0f)
            quadToRelative(-24.0f, 0.0f, -42.0f, -18.0f)
            reflectiveQuadToRelative(-18.0f, -42.0f)
            verticalLineToRelative(-600.0f)
            quadToRelative(0.0f, -24.0f, 18.0f, -42.0f)
            reflectiveQuadToRelative(42.0f, -18.0f)
            horizontalLineToRelative(600.0f)
            quadToRelative(24.0f, 0.0f, 42.0f, 18.0f)
            reflectiveQuadToRelative(18.0f, 42.0f)
            verticalLineToRelative(600.0f)
            quadToRelative(0.0f, 24.0f, -18.0f, 42.0f)
            reflectiveQuadToRelative(-42.0f, 18.0f)
            close()
            moveTo(180.0f, 780.0f)
            horizontalLineToRelative(600.0f)
            verticalLineToRelative(-600.0f)
            lineTo(180.0f, 180.0f)
            close()
            moveTo(180.0f, 180.0f)
            verticalLineToRelative(600.0f)
            close()
        }
    }
    .build()