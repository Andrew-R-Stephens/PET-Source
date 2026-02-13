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

fun getSprintVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "SprintVector",
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
            moveToRelative(216.0f, 800.0f)
            lineToRelative(-42.0f, -42.0f)
            lineToRelative(408.0f, -408.0f)
            lineTo(440.0f, 350.0f)
            verticalLineToRelative(80.0f)
            horizontalLineToRelative(-60.0f)
            verticalLineToRelative(-140.0f)
            horizontalLineToRelative(223.0f)
            quadToRelative(14.0f, 0.0f, 27.0f, 5.0f)
            reflectiveQuadToRelative(23.0f, 15.0f)
            lineToRelative(120.0f, 119.0f)
            quadToRelative(29.0f, 29.0f, 67.0f, 44.0f)
            reflectiveQuadToRelative(80.0f, 17.0f)
            verticalLineToRelative(60.0f)
            quadToRelative(-52.0f, -2.0f, -100.0f, -19.5f)
            reflectiveQuadTo(736.0f, 476.0f)
            lineToRelative(-46.0f, -46.0f)
            lineToRelative(-114.0f, 114.0f)
            lineToRelative(86.0f, 86.0f)
            lineToRelative(-244.0f, 141.0f)
            lineToRelative(-30.0f, -52.0f)
            lineToRelative(176.0f, -102.0f)
            lineToRelative(-82.0f, -82.0f)
            close()
            moveTo(120.0f, 520.0f)
            verticalLineToRelative(-60.0f)
            horizontalLineToRelative(200.0f)
            verticalLineToRelative(60.0f)
            close()
            moveTo(40.0f, 390.0f)
            verticalLineToRelative(-60.0f)
            horizontalLineToRelative(200.0f)
            verticalLineToRelative(60.0f)
            close()
            moveTo(790.0f, 310.0f)
            quadToRelative(-29.0f, 0.0f, -49.5f, -20.5f)
            reflectiveQuadTo(720.0f, 240.0f)
            reflectiveQuadToRelative(20.5f, -49.5f)
            reflectiveQuadTo(790.0f, 170.0f)
            reflectiveQuadToRelative(49.5f, 20.5f)
            reflectiveQuadTo(860.0f, 240.0f)
            reflectiveQuadToRelative(-20.5f, 49.5f)
            reflectiveQuadTo(790.0f, 310.0f)
            moveToRelative(-670.0f, -50.0f)
            verticalLineToRelative(-60.0f)
            horizontalLineToRelative(200.0f)
            verticalLineToRelative(60.0f)
            close()
        }
    }
    .build()
