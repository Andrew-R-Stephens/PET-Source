package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.vector

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

fun getMarkPriorityVector(groupColors: List<Color>): ImageVector =
    Builder(
        name = "MarkPriorityVector",
        defaultWidth = 48.0.dp,
        defaultHeight = 48.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f,
    )
    .apply {
        path(
            fill = SolidColor(groupColors[0]),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero,
        ) {
            moveTo(479.91f, 840.0f)
            quadToRelative(-28.91f, 0.0f, -49.41f, -20.59f)
            quadToRelative(-20.5f, -20.59f, -20.5f, -49.5f)
            reflectiveQuadToRelative(20.59f, -49.41f)
            quadToRelative(20.59f, -20.5f, 49.5f, -20.5f)
            reflectiveQuadToRelative(49.41f, 20.59f)
            quadToRelative(20.5f, 20.59f, 20.5f, 49.5f)
            reflectiveQuadToRelative(-20.59f, 49.41f)
            quadToRelative(-20.59f, 20.5f, -49.5f, 20.5f)
            close()
            moveTo(410.0f, 600.0f)
            verticalLineToRelative(-480.0f)
            horizontalLineToRelative(140.0f)
            verticalLineToRelative(480.0f)
            lineTo(410.0f, 600.0f)
            close()
        }
    }
    .build()