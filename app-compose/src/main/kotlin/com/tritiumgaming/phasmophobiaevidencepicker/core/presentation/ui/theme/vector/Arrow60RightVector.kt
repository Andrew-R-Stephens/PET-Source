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

fun getArrow60RightVector(groupColors: List<Color>): ImageVector =
    Builder(
        name = "Arrow60Right",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 300.0f,
        viewportHeight = 300.0f
    ).apply {
        path(
            fill = SolidColor(groupColors[0]), stroke = SolidColor(groupColors[0]),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(152.8f, 22.5f)
            curveToRelative(-4.1f, 3.8f, -4.6f, 5.0f, -2.3f, 5.9f)
            curveToRelative(0.8f, 0.3f, 1.5f, 1.4f, 1.5f, 2.4f)
            curveToRelative(0.0f, 1.0f, 2.0f, 3.8f, 4.5f, 6.2f)
            curveToRelative(2.5f, 2.4f, 4.5f, 5.1f, 4.5f, 6.0f)
            curveToRelative(0.0f, 2.1f, 6.9f, 8.7f, 9.5f, 9.2f)
            curveToRelative(1.1f, 0.2f, 2.1f, 1.3f, 2.3f, 2.5f)
            curveToRelative(0.2f, 1.2f, 1.0f, 2.7f, 1.8f, 3.3f)
            curveToRelative(0.8f, 0.7f, 1.4f, 2.0f, 1.4f, 2.9f)
            curveToRelative(0.0f, 2.1f, 7.0f, 9.1f, 9.1f, 9.1f)
            curveToRelative(2.0f, -0.0f, 11.6f, 10.0f, 12.1f, 12.5f)
            curveToRelative(0.2f, 1.1f, 1.2f, 2.1f, 2.4f, 2.3f)
            curveToRelative(1.1f, 0.2f, 4.7f, 3.0f, 7.9f, 6.2f)
            curveToRelative(3.3f, 3.3f, 6.6f, 6.0f, 7.5f, 6.0f)
            curveToRelative(2.0f, -0.0f, 9.0f, 7.1f, 9.0f, 9.1f)
            curveToRelative(0.0f, 0.8f, 1.4f, 2.8f, 3.0f, 4.4f)
            curveToRelative(1.7f, 1.6f, 3.0f, 3.6f, 3.0f, 4.5f)
            curveToRelative(0.0f, 1.9f, 6.8f, 8.7f, 9.2f, 9.2f)
            curveToRelative(1.9f, 0.3f, 17.8f, 15.5f, 17.8f, 16.9f)
            curveToRelative(0.0f, 0.5f, -4.4f, 0.9f, -9.9f, 0.9f)
            curveToRelative(-7.5f, -0.0f, -10.1f, -0.4f, -11.1f, -1.5f)
            curveToRelative(-1.1f, -1.3f, -11.7f, -1.5f, -84.0f, -1.5f)
            curveToRelative(-72.3f, -0.0f, -82.9f, 0.2f, -84.0f, 1.5f)
            curveToRelative(-1.0f, 1.2f, -4.7f, 1.5f, -21.0f, 1.5f)
            curveToRelative(-16.3f, -0.0f, -20.0f, 0.3f, -21.0f, 1.5f)
            curveToRelative(-0.7f, 0.8f, -2.7f, 1.5f, -4.4f, 1.5f)
            curveToRelative(-2.1f, -0.0f, -4.3f, 1.1f, -6.8f, 3.5f)
            curveToRelative(-4.1f, 3.8f, -4.6f, 5.0f, -2.4f, 5.8f)
            curveToRelative(0.8f, 0.3f, 1.6f, 1.6f, 1.8f, 2.9f)
            curveToRelative(0.2f, 1.5f, 1.1f, 2.4f, 2.5f, 2.6f)
            curveToRelative(1.2f, 0.2f, 2.7f, 1.0f, 3.3f, 1.8f)
            curveToRelative(1.9f, 2.2f, 163.2f, 2.1f, 165.0f, -0.1f)
            curveToRelative(1.0f, -1.2f, 3.8f, -1.5f, 13.5f, -1.5f)
            curveToRelative(9.7f, -0.0f, 12.5f, 0.3f, 13.5f, 1.5f)
            curveToRelative(1.9f, 2.2f, 37.8f, 2.2f, 38.6f, -0.0f)
            curveToRelative(0.9f, -2.3f, 3.4f, -1.8f, 3.4f, 0.8f)
            curveToRelative(0.0f, 2.6f, -15.5f, 18.7f, -18.0f, 18.7f)
            curveToRelative(-0.9f, -0.0f, -8.3f, 6.7f, -16.5f, 15.0f)
            curveToRelative(-8.2f, 8.2f, -15.7f, 15.0f, -16.5f, 15.0f)
            curveToRelative(-2.0f, -0.0f, -18.0f, 16.1f, -18.0f, 18.0f)
            curveToRelative(0.0f, 0.9f, -4.7f, 6.2f, -10.5f, 12.0f)
            curveToRelative(-5.8f, 5.8f, -10.5f, 11.2f, -10.5f, 12.0f)
            curveToRelative(0.0f, 0.9f, -1.3f, 2.9f, -3.0f, 4.5f)
            curveToRelative(-3.6f, 3.5f, -3.8f, 5.4f, -0.7f, 8.3f)
            curveToRelative(2.0f, 1.9f, 3.5f, 2.2f, 9.8f, 2.2f)
            lineToRelative(7.4f, -0.0f)
            lineToRelative(28.5f, -28.5f)
            curveToRelative(15.7f, -15.7f, 29.2f, -28.5f, 30.0f, -28.5f)
            curveToRelative(0.9f, -0.0f, 4.9f, -3.4f, 9.0f, -7.5f)
            curveToRelative(4.0f, -4.1f, 8.3f, -7.5f, 9.4f, -7.7f)
            curveToRelative(1.2f, -0.2f, 2.2f, -1.2f, 2.4f, -2.3f)
            curveToRelative(0.2f, -1.1f, 1.7f, -3.4f, 3.2f, -5.0f)
            curveToRelative(1.5f, -1.7f, 3.0f, -3.9f, 3.2f, -5.0f)
            curveToRelative(0.2f, -1.1f, 1.2f, -2.1f, 2.4f, -2.3f)
            curveToRelative(1.1f, -0.2f, 4.7f, -3.0f, 7.9f, -6.2f)
            curveToRelative(3.3f, -3.3f, 6.7f, -6.0f, 7.5f, -6.0f)
            curveToRelative(2.0f, -0.0f, 6.0f, -4.1f, 6.0f, -6.1f)
            curveToRelative(0.0f, -0.9f, 1.4f, -2.8f, 3.0f, -4.4f)
            curveToRelative(4.5f, -4.3f, 4.1f, -8.0f, -1.5f, -13.5f)
            curveToRelative(-2.5f, -2.4f, -4.5f, -5.1f, -4.5f, -6.0f)
            curveToRelative(0.0f, -2.1f, -3.8f, -5.7f, -6.5f, -6.2f)
            curveToRelative(-1.1f, -0.2f, -2.1f, -1.2f, -2.3f, -2.3f)
            curveToRelative(-0.2f, -1.1f, -2.3f, -4.0f, -4.7f, -6.5f)
            curveToRelative(-2.4f, -2.5f, -4.5f, -5.4f, -4.7f, -6.5f)
            curveToRelative(-0.2f, -1.1f, -1.2f, -2.1f, -2.4f, -2.3f)
            curveToRelative(-1.1f, -0.2f, -8.7f, -7.0f, -16.9f, -15.2f)
            curveToRelative(-8.3f, -8.3f, -15.7f, -15.0f, -16.6f, -15.0f)
            curveToRelative(-1.9f, -0.0f, -5.9f, -4.1f, -5.9f, -6.1f)
            curveToRelative(0.0f, -2.0f, -55.1f, -56.9f, -57.1f, -56.9f)
            curveToRelative(-0.9f, -0.0f, -2.2f, -0.7f, -2.9f, -1.5f)
            curveToRelative(-2.2f, -2.7f, -7.2f, -1.8f, -11.2f, 2.0f)
            close()
        }
    }
        .build()

