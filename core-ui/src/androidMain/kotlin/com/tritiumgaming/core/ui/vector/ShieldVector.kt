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

fun getShieldVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "ShieldVector",
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
            moveTo(480.0f, 879.0f)
            quadToRelative(-140.0f, -35.0f, -230.0f, -162.5f)
            reflectiveQuadTo(160.0f, 437.0f)
            verticalLineToRelative(-238.0f)
            lineToRelative(320.0f, -120.0f)
            lineToRelative(320.0f, 120.0f)
            verticalLineToRelative(238.0f)
            quadToRelative(0.0f, 152.0f, -90.0f, 279.5f)
            reflectiveQuadTo(480.0f, 879.0f)
            moveToRelative(0.0f, -62.0f)
            quadToRelative(115.0f, -38.0f, 187.5f, -143.5f)
            reflectiveQuadTo(740.0f, 437.0f)
            verticalLineToRelative(-196.0f)
            lineToRelative(-260.0f, -98.0f)
            lineToRelative(-260.0f, 98.0f)
            verticalLineToRelative(196.0f)
            quadToRelative(0.0f, 131.0f, 72.5f, 236.5f)
            reflectiveQuadTo(480.0f, 817.0f)
            moveToRelative(0.0f, -337.0f)
        }
    }
    .build()