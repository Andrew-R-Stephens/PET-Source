package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.color.IconVectorColors

fun getTruckOutlineVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "TruckOutlineVector",
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
            moveTo(140.5f, 764.58f)
            quadTo(106.0f, 730.17f, 106.0f, 681.0f)
            lineTo(40.0f, 681.0f)
            verticalLineToRelative(-461.0f)
            quadToRelative(0.0f, -24.0f, 18.0f, -42.0f)
            reflectiveQuadToRelative(42.0f, -18.0f)
            horizontalLineToRelative(579.0f)
            verticalLineToRelative(167.0f)
            horizontalLineToRelative(105.0f)
            lineToRelative(136.0f, 181.0f)
            verticalLineToRelative(173.0f)
            horizontalLineToRelative(-71.0f)
            quadToRelative(0.0f, 49.17f, -34.38f, 83.58f)
            quadTo(780.24f, 799.0f, 731.12f, 799.0f)
            reflectiveQuadToRelative(-83.62f, -34.42f)
            quadTo(613.0f, 730.17f, 613.0f, 681.0f)
            lineTo(342.0f, 681.0f)
            quadToRelative(0.0f, 49.0f, -34.38f, 83.5f)
            reflectiveQuadToRelative(-83.5f, 34.5f)
            reflectiveQuadToRelative(-83.62f, -34.42f)
            moveTo(265.0f, 722.0f)
            quadToRelative(17.0f, -17.0f, 17.0f, -41.0f)
            reflectiveQuadToRelative(-17.0f, -41.0f)
            reflectiveQuadToRelative(-41.0f, -17.0f)
            reflectiveQuadToRelative(-41.0f, 17.0f)
            reflectiveQuadToRelative(-17.0f, 41.0f)
            reflectiveQuadToRelative(17.0f, 41.0f)
            reflectiveQuadToRelative(41.0f, 17.0f)
            reflectiveQuadToRelative(41.0f, -17.0f)
            moveTo(100.0f, 621.0f)
            horizontalLineToRelative(22.0f)
            quadToRelative(17.0f, -27.0f, 43.04f, -43.0f)
            reflectiveQuadToRelative(58.0f, -16.0f)
            reflectiveQuadToRelative(58.46f, 16.5f)
            reflectiveQuadTo(325.0f, 621.0f)
            horizontalLineToRelative(294.0f)
            verticalLineToRelative(-401.0f)
            lineTo(100.0f, 220.0f)
            close()
            moveTo(772.0f, 722.0f)
            quadToRelative(17.0f, -17.0f, 17.0f, -41.0f)
            reflectiveQuadToRelative(-17.0f, -41.0f)
            reflectiveQuadToRelative(-41.0f, -17.0f)
            reflectiveQuadToRelative(-41.0f, 17.0f)
            reflectiveQuadToRelative(-17.0f, 41.0f)
            reflectiveQuadToRelative(17.0f, 41.0f)
            reflectiveQuadToRelative(41.0f, 17.0f)
            reflectiveQuadToRelative(41.0f, -17.0f)
            moveToRelative(-93.0f, -187.0f)
            horizontalLineToRelative(186.0f)
            lineTo(754.0f, 387.0f)
            horizontalLineToRelative(-75.0f)
            close()
            moveTo(360.0f, 431.0f)
        }
    }
    .build()