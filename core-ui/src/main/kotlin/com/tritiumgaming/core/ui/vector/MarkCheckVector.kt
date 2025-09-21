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

fun getMarkCheckVector(colors: IconVectorColors = IconVectorColors.defaults()): ImageVector =
    Builder(
        name = "MarkCheckVector",
        defaultWidth = 48.0.dp,
        defaultHeight = 48.0.dp,
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
            moveTo(382.0f, 720.0f)
            lineTo(154.0f, 492.0f)
            lineToRelative(57.0f, -57.0f)
            lineToRelative(171.0f, 171.0f)
            lineToRelative(367.0f, -367.0f)
            lineToRelative(57.0f, 57.0f)
            lineToRelative(-424.0f, 424.0f)
            close()
        }
    }
    .build()