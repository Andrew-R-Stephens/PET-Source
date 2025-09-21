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

fun getMarkXVector(colors: IconVectorColors = IconVectorColors.defaults()): ImageVector =
    Builder(
        name = "MarkXVector",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
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
            moveToRelative(256.0f, 760.0f)
            lineToRelative(-56.0f, -56.0f)
            lineToRelative(224.0f, -224.0f)
            lineToRelative(-224.0f, -224.0f)
            lineToRelative(56.0f, -56.0f)
            lineToRelative(224.0f, 224.0f)
            lineToRelative(224.0f, -224.0f)
            lineToRelative(56.0f, 56.0f)
            lineToRelative(-224.0f, 224.0f)
            lineToRelative(224.0f, 224.0f)
            lineToRelative(-56.0f, 56.0f)
            lineToRelative(-224.0f, -224.0f)
            lineToRelative(-224.0f, 224.0f)
            close()
        }
    }
    .build()