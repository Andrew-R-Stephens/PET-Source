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

fun getHalfMoonVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "ButtonScratched",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f,
    ).apply {
        path(
            fill = SolidColor(colors.strokeColor),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero,
        ) {
            moveTo(480.0f, 840.0f)
            quadToRelative(-150.0f, 0.0f, -255.0f, -105.0f)
            reflectiveQuadTo(120.0f, 480.0f)
            quadToRelative(0.0f, -150.0f, 105.0f, -255.0f)
            reflectiveQuadToRelative(255.0f, -105.0f)
            quadToRelative(8.0f, 0.0f, 17.0f, 0.5f)
            reflectiveQuadToRelative(23.0f, 1.5f)
            quadToRelative(-36.0f, 32.0f, -56.0f, 79.0f)
            reflectiveQuadToRelative(-20.0f, 99.0f)
            quadToRelative(0.0f, 90.0f, 63.0f, 153.0f)
            reflectiveQuadToRelative(153.0f, 63.0f)
            quadToRelative(52.0f, 0.0f, 99.0f, -18.5f)
            reflectiveQuadToRelative(79.0f, -51.5f)
            quadToRelative(1.0f, 12.0f, 1.5f, 19.5f)
            reflectiveQuadToRelative(0.5f, 14.5f)
            quadToRelative(0.0f, 150.0f, -105.0f, 255.0f)
            reflectiveQuadTo(480.0f, 840.0f)
            close()
            moveTo(480.0f, 780.0f)
            quadToRelative(109.0f, 0.0f, 190.0f, -67.5f)
            reflectiveQuadTo(771.0f, 554.0f)
            quadToRelative(-25.0f, 11.0f, -53.67f, 16.5f)
            quadTo(688.67f, 576.0f, 660.0f, 576.0f)
            quadToRelative(-114.69f, 0.0f, -195.34f, -80.66f)
            quadTo(384.0f, 414.69f, 384.0f, 300.0f)
            quadToRelative(0.0f, -24.0f, 5.0f, -51.5f)
            reflectiveQuadToRelative(18.0f, -62.5f)
            quadToRelative(-98.0f, 27.0f, -162.5f, 109.5f)
            reflectiveQuadTo(180.0f, 480.0f)
            quadToRelative(0.0f, 125.0f, 87.5f, 212.5f)
            reflectiveQuadTo(480.0f, 780.0f)
            close()
            moveTo(476.0f, 483.0f)
            close()
        }
    }
    .build()