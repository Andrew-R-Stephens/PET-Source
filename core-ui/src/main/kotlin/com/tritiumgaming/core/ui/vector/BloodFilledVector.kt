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

fun getBloodFilledVector(colors: IconVectorColors = IconVectorColors.defaults()): ImageVector =
    Builder(
        name = "ButtonScratched",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 48.0f,
        viewportHeight = 48.0f
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
            moveTo(12.575f, 39.3f)
            curveTo(9.525f, 36.167f, 8.0f, 32.267f, 8.0f, 27.6f)
            curveTo(8.0f, 24.267f, 9.325f, 20.642f, 11.975f, 16.725f)
            curveTo(14.625f, 12.808f, 18.633f, 8.567f, 24.0f, 4.0f)
            curveTo(29.367f, 8.567f, 33.375f, 12.808f, 36.025f, 16.725f)
            curveTo(38.675f, 20.642f, 40.0f, 24.267f, 40.0f, 27.6f)
            curveTo(40.0f, 32.267f, 38.475f, 36.167f, 35.425f, 39.3f)
            curveTo(32.375f, 42.433f, 28.567f, 44.0f, 24.0f, 44.0f)
            curveTo(19.433f, 44.0f, 15.625f, 42.433f, 12.575f, 39.3f)
            close()
        }
    }
    .build()