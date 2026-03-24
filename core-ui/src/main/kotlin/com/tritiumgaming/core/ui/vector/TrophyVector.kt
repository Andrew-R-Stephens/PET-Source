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

fun getTrophyVector(colors: IconVectorColors): ImageVector =
    Builder(
        name = "ActionPan",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 600.0f,
        viewportHeight = 600.0f
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
            moveTo(298.0f, 840.0f)
            verticalLineToRelative(-60.0f)
            horizontalLineToRelative(152.0f)
            verticalLineToRelative(-148.0f)
            quadToRelative(-54.0f, -11.0f, -96.0f, -46.5f)
            reflectiveQuadTo(296.0f, 497.0f)
            quadToRelative(-74.0f, -8.0f, -125.0f, -60.0f)
            reflectiveQuadToRelative(-51.0f, -125.0f)
            verticalLineToRelative(-44.0f)
            quadToRelative(0.0f, -25.0f, 17.5f, -42.5f)
            reflectiveQuadTo(180.0f, 208.0f)
            horizontalLineToRelative(104.0f)
            verticalLineToRelative(-88.0f)
            horizontalLineToRelative(392.0f)
            verticalLineToRelative(88.0f)
            horizontalLineToRelative(104.0f)
            quadToRelative(25.0f, 0.0f, 42.5f, 17.5f)
            reflectiveQuadTo(840.0f, 268.0f)
            verticalLineToRelative(44.0f)
            quadToRelative(0.0f, 73.0f, -51.0f, 125.0f)
            reflectiveQuadToRelative(-125.0f, 60.0f)
            quadToRelative(-16.0f, 53.0f, -58.0f, 88.5f)
            reflectiveQuadTo(510.0f, 632.0f)
            verticalLineToRelative(148.0f)
            horizontalLineToRelative(152.0f)
            verticalLineToRelative(60.0f)
            lineTo(298.0f, 840.0f)
            close()
            moveTo(284.0f, 434.0f)
            verticalLineToRelative(-166.0f)
            lineTo(180.0f, 268.0f)
            verticalLineToRelative(44.0f)
            quadToRelative(0.0f, 45.0f, 29.5f, 78.5f)
            reflectiveQuadTo(284.0f, 434.0f)
            close()
            moveTo(576.5f, 535.0f)
            quadToRelative(39.5f, -40.0f, 39.5f, -97.0f)
            verticalLineToRelative(-258.0f)
            lineTo(344.0f, 180.0f)
            verticalLineToRelative(258.0f)
            quadToRelative(0.0f, 57.0f, 39.5f, 97.0f)
            reflectiveQuadToRelative(96.5f, 40.0f)
            quadToRelative(57.0f, 0.0f, 96.5f, -40.0f)
            close()
            moveTo(676.0f, 434.0f)
            quadToRelative(45.0f, -10.0f, 74.5f, -43.5f)
            reflectiveQuadTo(780.0f, 312.0f)
            verticalLineToRelative(-44.0f)
            lineTo(676.0f, 268.0f)
            verticalLineToRelative(166.0f)
            close()
            moveTo(480.0f, 377.0f)
            close()
        }
    }
    .build()