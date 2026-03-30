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

fun getBloodMoonFilledVector(colors: IconVectorColors = IconVectorColors.defaults()): ImageVector =
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
                        moveTo(24.0f, 42.0f)
                        curveTo(19.0f, 42.0f, 14.75f, 40.25f, 11.25f, 36.75f)
                        curveTo(7.75f, 33.25f, 6.0f, 29.0f, 6.0f, 24.0f)
                        curveTo(6.0f, 19.0f, 7.75f, 14.75f, 11.25f, 11.25f)
                        curveTo(14.75f, 7.75f, 19.0f, 6.0f, 24.0f, 6.0f)
                        curveTo(24.267f, 6.0f, 24.55f, 6.008f, 24.85f, 6.025f)
                        curveTo(25.15f, 6.042f, 25.533f, 6.067f, 26.0f, 6.1f)
                        curveTo(24.8f, 7.167f, 23.867f, 8.483f, 23.2f, 10.05f)
                        curveTo(22.533f, 11.617f, 22.2f, 13.267f, 22.2f, 15.0f)
                        curveTo(22.2f, 18.0f, 23.25f, 20.55f, 25.35f, 22.65f)
                        curveTo(27.45f, 24.75f, 30.0f, 25.8f, 33.0f, 25.8f)
                        curveTo(34.733f, 25.8f, 36.383f, 25.492f, 37.95f, 24.875f)
                        curveTo(39.517f, 24.258f, 40.833f, 23.4f, 41.9f, 22.3f)
                        curveTo(41.933f, 22.7f, 41.958f, 23.025f, 41.975f, 23.275f)
                        curveTo(41.992f, 23.525f, 42.0f, 23.767f, 42.0f, 24.0f)
                        curveTo(42.0f, 29.0f, 40.25f, 33.25f, 36.75f, 36.75f)
                        curveTo(33.25f, 40.25f, 29.0f, 42.0f, 24.0f, 42.0f)
                        close()
                    }
                    path(
                        fill = SolidColor(colors.fillColor),
                        stroke = null,
                        strokeLineWidth = 0.0f,
                        strokeLineCap = Butt,
                        strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f,
                        pathFillType = NonZero,
                    ) {
                        moveTo(27.431f, 20.415f)
                        curveTo(25.81f, 18.692f, 25.0f, 16.547f, 25.0f, 13.98f)
                        curveTo(25.0f, 12.147f, 25.704f, 10.153f, 27.112f, 7.999f)
                        curveTo(28.52f, 5.845f, 30.649f, 3.512f, 33.5f, 1.0f)
                        curveTo(36.351f, 3.512f, 38.48f, 5.845f, 39.888f, 7.999f)
                        curveTo(41.296f, 10.153f, 42.0f, 12.147f, 42.0f, 13.98f)
                        curveTo(42.0f, 16.547f, 41.19f, 18.692f, 39.569f, 20.415f)
                        curveTo(37.949f, 22.138f, 35.926f, 23.0f, 33.5f, 23.0f)
                        curveTo(31.074f, 23.0f, 29.051f, 22.138f, 27.431f, 20.415f)
                        close()
                    }
                }
                .build()