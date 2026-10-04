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

fun getVisibleVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "VisibleVector",
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
            moveTo(600.5f, 580.5f)
            quadTo(650.0f, 531.0f, 650.0f, 460.0f)
            reflectiveQuadToRelative(-49.5f, -120.5f)
            reflectiveQuadTo(480.0f, 290.0f)
            reflectiveQuadToRelative(-120.5f, 49.5f)
            reflectiveQuadTo(310.0f, 460.0f)
            reflectiveQuadToRelative(49.5f, 120.5f)
            reflectiveQuadTo(480.0f, 630.0f)
            reflectiveQuadToRelative(120.5f, -49.5f)
            moveToRelative(-200.0f, -41.0f)
            quadTo(368.0f, 507.0f, 368.0f, 460.0f)
            reflectiveQuadToRelative(32.5f, -79.5f)
            reflectiveQuadTo(480.0f, 348.0f)
            reflectiveQuadToRelative(79.5f, 32.5f)
            reflectiveQuadTo(592.0f, 460.0f)
            reflectiveQuadToRelative(-32.5f, 79.5f)
            reflectiveQuadTo(480.0f, 572.0f)
            reflectiveQuadToRelative(-79.5f, -32.5f)
            moveTo(216.0f, 677.0f)
            quadTo(98.0f, 594.0f, 40.0f, 460.0f)
            quadToRelative(58.0f, -134.0f, 176.0f, -217.0f)
            reflectiveQuadToRelative(264.0f, -83.0f)
            reflectiveQuadToRelative(264.0f, 83.0f)
            reflectiveQuadToRelative(176.0f, 217.0f)
            quadToRelative(-58.0f, 134.0f, -176.0f, 217.0f)
            reflectiveQuadToRelative(-264.0f, 83.0f)
            reflectiveQuadToRelative(-264.0f, -83.0f)
            moveToRelative(486.5f, -42.5f)
            quadTo(804.0f, 569.0f, 857.0f, 460.0f)
            quadToRelative(-53.0f, -109.0f, -154.5f, -174.5f)
            reflectiveQuadTo(480.0f, 220.0f)
            reflectiveQuadToRelative(-222.5f, 65.5f)
            reflectiveQuadTo(102.0f, 460.0f)
            quadToRelative(54.0f, 109.0f, 155.5f, 174.5f)
            reflectiveQuadTo(480.0f, 700.0f)
            reflectiveQuadToRelative(222.5f, -65.5f)
        }
    }
    .build()