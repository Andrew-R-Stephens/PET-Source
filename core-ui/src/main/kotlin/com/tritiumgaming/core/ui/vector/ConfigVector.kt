package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.color.IconVectorColors

fun getConfigVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
            name = "ConfigsVector",
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
                moveTo(427.0f, 840.0f)
                verticalLineToRelative(-225.0f)
                horizontalLineToRelative(60.0f)
                verticalLineToRelative(83.0f)
                horizontalLineToRelative(353.0f)
                verticalLineToRelative(60.0f)
                lineTo(487.0f, 758.0f)
                verticalLineToRelative(82.0f)
                close()
                moveTo(120.0f, 758.0f)
                verticalLineToRelative(-60.0f)
                horizontalLineToRelative(247.0f)
                verticalLineToRelative(60.0f)
                close()
                moveTo(307.0f, 592.0f)
                verticalLineToRelative(-82.0f)
                lineTo(120.0f, 510.0f)
                verticalLineToRelative(-60.0f)
                horizontalLineToRelative(187.0f)
                verticalLineToRelative(-84.0f)
                horizontalLineToRelative(60.0f)
                verticalLineToRelative(226.0f)
                close()
                moveTo(427.0f, 510.0f)
                verticalLineToRelative(-60.0f)
                horizontalLineToRelative(413.0f)
                verticalLineToRelative(60.0f)
                close()
                moveTo(593.0f, 345.0f)
                verticalLineToRelative(-225.0f)
                horizontalLineToRelative(60.0f)
                verticalLineToRelative(82.0f)
                horizontalLineToRelative(187.0f)
                verticalLineToRelative(60.0f)
                lineTo(653.0f, 262.0f)
                verticalLineToRelative(83.0f)
                close()
                moveTo(120.0f, 262.0f)
                verticalLineToRelative(-60.0f)
                horizontalLineToRelative(413.0f)
                verticalLineToRelative(60.0f)
                close()
            }
        }
        .build()
