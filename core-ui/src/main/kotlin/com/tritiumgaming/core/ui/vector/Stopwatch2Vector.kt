package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.color.IconVectorColors

fun getStopwatch2Vector(
    colors: IconVectorColors
): ImageVector = ImageVector.Builder(
            name = "Stopwatch",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(360f, 100f)
                verticalLineToRelative(-60f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(60f)
                lineTo(360f, 100f)
                close()
                moveTo(450f, 547f)
                horizontalLineToRelative(60f)
                verticalLineToRelative(-230f)
                horizontalLineToRelative(-60f)
                verticalLineToRelative(230f)
                close()
                moveTo(340.5f, 850.5f)
                quadTo(275f, 822f, 226f, 773f)
                reflectiveQuadToRelative(-77.5f, -114.5f)
                quadTo(120f, 593f, 120f, 519f)
                reflectiveQuadToRelative(28.5f, -139.5f)
                quadTo(177f, 314f, 226f, 265f)
                reflectiveQuadToRelative(114.5f, -77.5f)
                quadTo(406f, 159f, 480f, 159f)
                quadToRelative(67f, 0f, 126f, 22.5f)
                reflectiveQuadTo(711f, 244f)
                lineToRelative(51f, -51f)
                lineToRelative(42f, 42f)
                lineToRelative(-51f, 51f)
                quadToRelative(36f, 40f, 61.5f, 97f)
                reflectiveQuadTo(840f, 519f)
                quadToRelative(0f, 74f, -28.5f, 139.5f)
                reflectiveQuadTo(734f, 773f)
                quadToRelative(-49f, 49f, -114.5f, 77.5f)
                reflectiveQuadTo(480f, 879f)
                quadToRelative(-74f, 0f, -139.5f, -28.5f)
                close()
                moveTo(692.5f, 731.5f)
                quadTo(780f, 644f, 780f, 519f)
                reflectiveQuadToRelative(-87.5f, -212.5f)
                quadTo(605f, 219f, 480f, 219f)
                reflectiveQuadToRelative(-212.5f, 87.5f)
                quadTo(180f, 394f, 180f, 519f)
                reflectiveQuadToRelative(87.5f, 212.5f)
                quadTo(355f, 819f, 480f, 819f)
                reflectiveQuadToRelative(212.5f, -87.5f)
                close()
                moveTo(480f, 520f)
                close()
            }
        }.build()
