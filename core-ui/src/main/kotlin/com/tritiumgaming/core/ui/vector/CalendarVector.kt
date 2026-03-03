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

fun getCalendarVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "CalendarVector",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f,
    ).apply {
        path(
            fill = SolidColor(colors.fillColor),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero,
        ) {
            moveTo(180.0f, 880.0f)
            quadToRelative(-24.0f, 0.0f, -42.0f, -18.0f)
            reflectiveQuadToRelative(-18.0f, -42.0f)
            verticalLineToRelative(-620.0f)
            quadToRelative(0.0f, -24.0f, 18.0f, -42.0f)
            reflectiveQuadToRelative(42.0f, -18.0f)
            horizontalLineToRelative(65.0f)
            verticalLineToRelative(-60.0f)
            horizontalLineToRelative(65.0f)
            verticalLineToRelative(60.0f)
            horizontalLineToRelative(340.0f)
            verticalLineToRelative(-60.0f)
            horizontalLineToRelative(65.0f)
            verticalLineToRelative(60.0f)
            horizontalLineToRelative(65.0f)
            quadToRelative(24.0f, 0.0f, 42.0f, 18.0f)
            reflectiveQuadToRelative(18.0f, 42.0f)
            verticalLineToRelative(620.0f)
            quadToRelative(0.0f, 24.0f, -18.0f, 42.0f)
            reflectiveQuadToRelative(-42.0f, 18.0f)
            lineTo(180.0f, 880.0f)
            close()
            moveTo(180.0f, 820.0f)
            horizontalLineToRelative(600.0f)
            verticalLineToRelative(-430.0f)
            lineTo(180.0f, 390.0f)
            verticalLineToRelative(430.0f)
            close()
            moveTo(180.0f, 330.0f)
            horizontalLineToRelative(600.0f)
            verticalLineToRelative(-130.0f)
            lineTo(180.0f, 200.0f)
            verticalLineToRelative(130.0f)
            close()
            moveTo(180.0f, 330.0f)
            verticalLineToRelative(-130.0f)
            verticalLineToRelative(130.0f)
            close()
            moveTo(480.0f, 560.0f)
            quadToRelative(-17.0f, 0.0f, -28.5f, -11.5f)
            reflectiveQuadTo(440.0f, 520.0f)
            quadToRelative(0.0f, -17.0f, 11.5f, -28.5f)
            reflectiveQuadTo(480.0f, 480.0f)
            quadToRelative(17.0f, 0.0f, 28.5f, 11.5f)
            reflectiveQuadTo(520.0f, 520.0f)
            quadToRelative(0.0f, 17.0f, -11.5f, 28.5f)
            reflectiveQuadTo(480.0f, 560.0f)
            close()
            moveTo(291.5f, 548.5f)
            quadTo(280.0f, 537.0f, 280.0f, 520.0f)
            reflectiveQuadToRelative(11.5f, -28.5f)
            quadTo(303.0f, 480.0f, 320.0f, 480.0f)
            reflectiveQuadToRelative(28.5f, 11.5f)
            quadTo(360.0f, 503.0f, 360.0f, 520.0f)
            reflectiveQuadToRelative(-11.5f, 28.5f)
            quadTo(337.0f, 560.0f, 320.0f, 560.0f)
            reflectiveQuadToRelative(-28.5f, -11.5f)
            close()
            moveTo(640.0f, 560.0f)
            quadToRelative(-17.0f, 0.0f, -28.5f, -11.5f)
            reflectiveQuadTo(600.0f, 520.0f)
            quadToRelative(0.0f, -17.0f, 11.5f, -28.5f)
            reflectiveQuadTo(640.0f, 480.0f)
            quadToRelative(17.0f, 0.0f, 28.5f, 11.5f)
            reflectiveQuadTo(680.0f, 520.0f)
            quadToRelative(0.0f, 17.0f, -11.5f, 28.5f)
            reflectiveQuadTo(640.0f, 560.0f)
            close()
            moveTo(480.0f, 720.0f)
            quadToRelative(-17.0f, 0.0f, -28.5f, -11.5f)
            reflectiveQuadTo(440.0f, 680.0f)
            quadToRelative(0.0f, -17.0f, 11.5f, -28.5f)
            reflectiveQuadTo(480.0f, 640.0f)
            quadToRelative(17.0f, 0.0f, 28.5f, 11.5f)
            reflectiveQuadTo(520.0f, 680.0f)
            quadToRelative(0.0f, 17.0f, -11.5f, 28.5f)
            reflectiveQuadTo(480.0f, 720.0f)
            close()
            moveTo(291.5f, 708.5f)
            quadTo(280.0f, 697.0f, 280.0f, 680.0f)
            reflectiveQuadToRelative(11.5f, -28.5f)
            quadTo(303.0f, 640.0f, 320.0f, 640.0f)
            reflectiveQuadToRelative(28.5f, 11.5f)
            quadTo(360.0f, 663.0f, 360.0f, 680.0f)
            reflectiveQuadToRelative(-11.5f, 28.5f)
            quadTo(337.0f, 720.0f, 320.0f, 720.0f)
            reflectiveQuadToRelative(-28.5f, -11.5f)
            close()
            moveTo(640.0f, 720.0f)
            quadToRelative(-17.0f, 0.0f, -28.5f, -11.5f)
            reflectiveQuadTo(600.0f, 680.0f)
            quadToRelative(0.0f, -17.0f, 11.5f, -28.5f)
            reflectiveQuadTo(640.0f, 640.0f)
            quadToRelative(17.0f, 0.0f, 28.5f, 11.5f)
            reflectiveQuadTo(680.0f, 680.0f)
            quadToRelative(0.0f, 17.0f, -11.5f, 28.5f)
            reflectiveQuadTo(640.0f, 720.0f)
            close()
        }
    }
    .build()
