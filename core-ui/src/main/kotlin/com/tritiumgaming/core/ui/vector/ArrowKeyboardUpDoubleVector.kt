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

fun getArrowKeyboardUpDoubleVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "ArrowKeyboardUpDoubleVector",
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
                moveToRelative(282.0f, 735.0f)
                lineToRelative(-42.0f, -42.0f)
                lineToRelative(240.0f, -240.0f)
                lineToRelative(240.0f, 240.0f)
                lineToRelative(-42.0f, 42.0f)
                lineToRelative(-198.0f, -198.0f)
                lineToRelative(-198.0f, 198.0f)
                close()
                moveTo(282.0f, 482.0f)
                lineTo(240.0f, 440.0f)
                lineTo(480.0f, 200.0f)
                lineTo(720.0f, 440.0f)
                lineTo(678.0f, 482.0f)
                lineTo(480.0f, 284.0f)
                lineTo(282.0f, 482.0f)
                close()
            }
        }
        .build()
