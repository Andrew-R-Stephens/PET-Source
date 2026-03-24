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

fun getArrowKeyboardUpSingleVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "ArrowKeyboardUpSingleVector",
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
                moveTo(480.0f, 406.0f)
                lineTo(283.0f, 603.0f)
                lineToRelative(-43.0f, -43.0f)
                lineToRelative(240.0f, -240.0f)
                lineToRelative(240.0f, 240.0f)
                lineToRelative(-43.0f, 43.0f)
                lineToRelative(-197.0f, -197.0f)
                close()
            }
        }
        .build()
