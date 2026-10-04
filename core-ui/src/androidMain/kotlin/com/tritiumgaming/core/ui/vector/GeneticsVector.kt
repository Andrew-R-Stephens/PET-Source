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

fun getGeneticsVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "Globe",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f
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
            moveTo(200.0f, 920.0f)
            verticalLineToRelative(-40.0f)
            quadToRelative(0.0f, -140.0f, 65.0f, -226.0f)
            reflectiveQuadToRelative(169.0f, -174.0f)
            quadToRelative(-104.0f, -88.0f, -169.0f, -174.0f)
            reflectiveQuadToRelative(-65.0f, -226.0f)
            verticalLineToRelative(-40.0f)
            horizontalLineToRelative(60.0f)
            verticalLineToRelative(40.0f)
            quadToRelative(0.0f, 11.0f, 0.5f, 20.5f)
            reflectiveQuadTo(262.0f, 120.0f)
            horizontalLineToRelative(436.0f)
            quadToRelative(1.0f, -10.0f, 1.5f, -19.5f)
            reflectiveQuadToRelative(0.5f, -20.5f)
            verticalLineToRelative(-40.0f)
            horizontalLineToRelative(60.0f)
            verticalLineToRelative(40.0f)
            quadToRelative(0.0f, 140.0f, -65.0f, 226.0f)
            reflectiveQuadTo(526.0f, 480.0f)
            quadToRelative(104.0f, 88.0f, 169.0f, 174.0f)
            reflectiveQuadToRelative(65.0f, 226.0f)
            verticalLineToRelative(40.0f)
            horizontalLineToRelative(-60.0f)
            verticalLineToRelative(-40.0f)
            quadToRelative(0.0f, -11.0f, -0.5f, -20.5f)
            reflectiveQuadTo(698.0f, 840.0f)
            lineTo(262.0f, 840.0f)
            quadToRelative(-1.0f, 10.0f, -1.5f, 19.5f)
            reflectiveQuadTo(260.0f, 880.0f)
            verticalLineToRelative(40.0f)
            close()
            moveTo(320.0f, 280.0f)
            horizontalLineToRelative(320.0f)
            quadToRelative(16.0f, -23.0f, 27.5f, -47.5f)
            reflectiveQuadTo(687.0f, 180.0f)
            lineTo(273.0f, 180.0f)
            quadToRelative(8.0f, 28.0f, 19.5f, 52.5f)
            reflectiveQuadTo(320.0f, 280.0f)
            moveToRelative(160.0f, 161.0f)
            quadToRelative(31.0f, -26.0f, 59.0f, -50.5f)
            reflectiveQuadToRelative(52.0f, -50.5f)
            lineTo(369.0f, 340.0f)
            quadToRelative(24.0f, 26.0f, 51.5f, 50.5f)
            reflectiveQuadTo(480.0f, 441.0f)
            moveTo(369.0f, 620.0f)
            horizontalLineToRelative(222.0f)
            quadToRelative(-24.0f, -26.0f, -52.0f, -50.5f)
            reflectiveQuadTo(480.0f, 519.0f)
            quadToRelative(-31.0f, 26.0f, -59.0f, 50.5f)
            reflectiveQuadTo(369.0f, 620.0f)
            moveToRelative(-96.0f, 160.0f)
            horizontalLineToRelative(414.0f)
            quadToRelative(-8.0f, -28.0f, -19.5f, -52.5f)
            reflectiveQuadTo(640.0f, 680.0f)
            lineTo(320.0f, 680.0f)
            quadToRelative(-16.0f, 23.0f, -27.5f, 47.5f)
            reflectiveQuadTo(273.0f, 780.0f)
        }
    }
        .build()
