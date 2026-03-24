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
import com.tritiumgaming.core.ui.vector.color.IconVectorColors

fun getArrowKeyboardDownDoubleVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "ArrowKeyboardDownDoubleVector",
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
                moveTo(480.0f, 760.0f)
                lineTo(240.0f, 520.0f)
                lineToRelative(42.0f, -42.0f)
                lineToRelative(198.0f, 198.0f)
                lineToRelative(198.0f, -198.0f)
                lineToRelative(42.0f, 42.0f)
                lineToRelative(-240.0f, 240.0f)
                close()
                moveTo(480.0f, 507.0f)
                lineTo(240.0f, 267.0f)
                lineToRelative(42.0f, -42.0f)
                lineToRelative(198.0f, 198.0f)
                lineToRelative(198.0f, -198.0f)
                lineToRelative(42.0f, 42.0f)
                lineToRelative(-240.0f, 240.0f)
                close()
            }
        }
        .build()
