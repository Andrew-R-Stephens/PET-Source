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

fun getTimeAvgVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "TimeAvgVector",
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
            moveTo(181.0f, 490.0f)
            horizontalLineToRelative(139.0f)
            quadToRelative(8.0f, 0.0f, 15.5f, 4.0f)
            reflectiveQuadToRelative(11.5f, 12.0f)
            lineToRelative(53.0f, 107.0f)
            lineToRelative(133.0f, -267.0f)
            quadToRelative(8.0f, -17.0f, 27.0f, -17.0f)
            reflectiveQuadToRelative(27.0f, 17.0f)
            lineToRelative(72.0f, 144.0f)
            horizontalLineToRelative(120.0f)
            quadToRelative(-11.0f, -115.0f, -96.0f, -193.0f)
            reflectiveQuadToRelative(-203.0f, -78.0f)
            reflectiveQuadToRelative(-203.0f, 78.0f)
            reflectiveQuadToRelative(-96.0f, 193.0f)
            moveToRelative(501.5f, 251.5f)
            quadTo(767.0f, 664.0f, 778.0f, 550.0f)
            lineTo(640.0f, 550.0f)
            quadToRelative(-8.0f, 0.0f, -15.5f, -4.0f)
            reflectiveQuadTo(613.0f, 534.0f)
            lineToRelative(-53.0f, -107.0f)
            lineToRelative(-133.0f, 267.0f)
            quadToRelative(-8.0f, 17.0f, -27.0f, 17.0f)
            reflectiveQuadToRelative(-27.0f, -17.0f)
            lineToRelative(-72.0f, -144.0f)
            lineTo(182.0f, 550.0f)
            quadToRelative(11.0f, 114.0f, 95.5f, 191.5f)
            reflectiveQuadTo(480.0f, 819.0f)
            reflectiveQuadToRelative(202.5f, -77.5f)
            moveToRelative(-342.0f, 109.0f)
            quadTo(275.0f, 822.0f, 226.0f, 773.0f)
            reflectiveQuadToRelative(-77.5f, -114.5f)
            reflectiveQuadTo(120.0f, 519.0f)
            horizontalLineToRelative(60.0f)
            quadToRelative(0.0f, 125.0f, 87.5f, 212.5f)
            reflectiveQuadTo(480.0f, 819.0f)
            reflectiveQuadToRelative(212.5f, -87.5f)
            reflectiveQuadTo(780.0f, 519.0f)
            horizontalLineToRelative(60.0f)
            quadToRelative(0.0f, 74.0f, -28.5f, 139.5f)
            reflectiveQuadTo(734.0f, 773.0f)
            reflectiveQuadToRelative(-114.5f, 77.5f)
            reflectiveQuadTo(480.0f, 879.0f)
            reflectiveQuadToRelative(-139.5f, -28.5f)
            moveTo(120.0f, 519.0f)
            quadToRelative(0.0f, -74.0f, 28.5f, -139.5f)
            reflectiveQuadTo(226.0f, 265.0f)
            reflectiveQuadToRelative(114.5f, -77.5f)
            reflectiveQuadTo(480.0f, 159.0f)
            quadToRelative(67.0f, 0.0f, 126.0f, 22.5f)
            reflectiveQuadTo(711.0f, 244.0f)
            lineToRelative(51.0f, -51.0f)
            lineToRelative(42.0f, 42.0f)
            lineToRelative(-51.0f, 51.0f)
            quadToRelative(36.0f, 40.0f, 61.5f, 97.0f)
            reflectiveQuadTo(840.0f, 519.0f)
            horizontalLineToRelative(-60.0f)
            quadToRelative(0.0f, -125.0f, -87.5f, -212.5f)
            reflectiveQuadTo(480.0f, 219.0f)
            reflectiveQuadToRelative(-212.5f, 87.5f)
            reflectiveQuadTo(180.0f, 519.0f)
            close()
            moveTo(360.0f, 100.0f)
            verticalLineToRelative(-60.0f)
            horizontalLineToRelative(240.0f)
            verticalLineToRelative(60.0f)
            close()
            moveTo(267.5f, 731.5f)
            quadTo(180.0f, 644.0f, 180.0f, 519.0f)
            reflectiveQuadToRelative(87.5f, -212.5f)
            reflectiveQuadTo(480.0f, 219.0f)
            reflectiveQuadToRelative(212.5f, 87.5f)
            reflectiveQuadTo(780.0f, 519.0f)
            reflectiveQuadToRelative(-87.5f, 212.5f)
            reflectiveQuadTo(480.0f, 819.0f)
            reflectiveQuadToRelative(-212.5f, -87.5f)
            moveTo(480.0f, 520.0f)
        }
    }
    .build()
