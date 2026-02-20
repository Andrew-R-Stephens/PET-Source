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

fun getFootprintsVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "FootprintVector",
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
            moveTo(260.0f, 100.0f)
            quadToRelative(-52.0f, 0.0f, -86.0f, 51.0f)
            reflectiveQuadToRelative(-34.0f, 129.0f)
            reflectiveQuadToRelative(23.0f, 131.5f)
            reflectiveQuadToRelative(32.0f, 67.5f)
            lineToRelative(144.0f, -30.0f)
            quadToRelative(14.0f, -38.0f, 27.5f, -84.0f)
            reflectiveQuadToRelative(13.5f, -85.0f)
            quadToRelative(0.0f, -67.0f, -31.5f, -123.5f)
            reflectiveQuadTo(260.0f, 100.0f)
            moveToRelative(55.0f, 560.0f)
            quadToRelative(28.0f, 0.0f, 46.5f, -20.5f)
            reflectiveQuadTo(380.0f, 587.0f)
            quadToRelative(0.0f, -20.0f, -9.0f, -41.5f)
            reflectiveQuadTo(351.0f, 507.0f)
            lineToRelative(-131.0f, 27.0f)
            quadToRelative(-1.0f, 43.0f, 21.5f, 84.5f)
            reflectiveQuadTo(315.0f, 660.0f)
            moveToRelative(385.0f, -360.0f)
            quadToRelative(-57.0f, 0.0f, -88.5f, 56.5f)
            reflectiveQuadTo(580.0f, 480.0f)
            quadToRelative(0.0f, 40.0f, 14.0f, 85.5f)
            reflectiveQuadToRelative(28.0f, 83.5f)
            lineToRelative(143.0f, 29.0f)
            quadToRelative(10.0f, -15.0f, 32.5f, -68.0f)
            reflectiveQuadTo(820.0f, 480.0f)
            quadToRelative(0.0f, -78.0f, -34.0f, -129.0f)
            reflectiveQuadToRelative(-86.0f, -51.0f)
            moveToRelative(-55.0f, 560.0f)
            quadToRelative(51.0f, 0.0f, 73.0f, -42.0f)
            reflectiveQuadToRelative(22.0f, -85.0f)
            lineToRelative(-131.0f, -26.0f)
            quadToRelative(-10.0f, 17.0f, -19.5f, 38.5f)
            reflectiveQuadTo(580.0f, 787.0f)
            quadToRelative(0.0f, 32.0f, 18.5f, 52.5f)
            reflectiveQuadTo(645.0f, 860.0f)
            moveTo(315.0f, 720.0f)
            quadToRelative(-77.0f, 0.0f, -117.0f, -57.0f)
            reflectiveQuadToRelative(-38.0f, -128.0f)
            lineToRelative(-18.0f, -27.0f)
            quadToRelative(-11.0f, -17.0f, -36.5f, -77.0f)
            reflectiveQuadTo(80.0f, 280.0f)
            quadToRelative(0.0f, -103.0f, 51.0f, -171.5f)
            reflectiveQuadTo(260.0f, 40.0f)
            quadToRelative(85.0f, 0.0f, 132.5f, 75.5f)
            reflectiveQuadTo(440.0f, 280.0f)
            quadToRelative(0.0f, 58.0f, -16.0f, 107.0f)
            reflectiveQuadToRelative(-28.0f, 79.0f)
            lineToRelative(8.0f, 13.0f)
            quadToRelative(8.0f, 14.0f, 22.0f, 44.5f)
            reflectiveQuadToRelative(14.0f, 63.5f)
            quadToRelative(0.0f, 57.0f, -35.5f, 95.0f)
            reflectiveQuadTo(315.0f, 720.0f)
            moveTo(645.0f, 920.0f)
            quadToRelative(-54.0f, 0.0f, -89.5f, -38.0f)
            reflectiveQuadTo(520.0f, 787.0f)
            quadToRelative(0.0f, -33.0f, 14.0f, -63.5f)
            reflectiveQuadToRelative(22.0f, -44.5f)
            lineToRelative(8.0f, -13.0f)
            quadToRelative(-12.0f, -30.0f, -28.0f, -79.0f)
            reflectiveQuadToRelative(-16.0f, -107.0f)
            quadToRelative(0.0f, -89.0f, 47.5f, -164.5f)
            reflectiveQuadTo(700.0f, 240.0f)
            quadToRelative(78.0f, 0.0f, 129.0f, 68.5f)
            reflectiveQuadTo(880.0f, 480.0f)
            quadToRelative(0.0f, 91.0f, -25.5f, 150.5f)
            reflectiveQuadTo(818.0f, 707.0f)
            lineToRelative(-18.0f, 28.0f)
            quadToRelative(1.0f, 71.0f, -38.5f, 128.0f)
            reflectiveQuadTo(645.0f, 920.0f)
        }
    }
    .build()