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

fun getLogoPatreonVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "LogoPatreonVector",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 1080.0f,
        viewportHeight = 1080.0f,
    ).apply {
        path(
            fill = SolidColor(colors.fillColor),
            stroke = SolidColor(colors.strokeColor),
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(1033.1f, 324.5f)
            curveToRelative(-0.2f, -137.9f, -107.6f, -250.9f, -233.6f, -291.7f)
            curveToRelative(-156.5f, -50.6f, -362.9f, -43.3f, -512.3f, 27.2f)
            curveTo(106.1f, 145.4f, 49.2f, 332.6f, 47.1f, 519.3f)
            curveToRelative(-1.7f, 153.5f, 13.6f, 557.8f, 241.6f, 560.7f)
            curveToRelative(169.4f, 2.2f, 194.7f, -216.2f, 273.1f, -321.3f)
            curveToRelative(55.8f, -74.8f, 127.6f, -95.9f, 216.0f, -117.8f)
            curveTo(929.7f, 603.2f, 1033.3f, 483.3f, 1033.1f, 324.5f)
            close()
        }
    }
    .build()
