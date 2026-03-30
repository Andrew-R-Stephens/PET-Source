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

fun getBloodMoonVector(colors: IconVectorColors = IconVectorColors.defaults()): ImageVector =
    Builder(
        name = "ButtonScratched",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 48.0f,
        viewportHeight = 48.0f
    ).apply {
        path(
            fill = SolidColor(colors.strokeColor),
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
            moveTo(24.0f, 39.0f)
            curveTo(27.633f, 39.0f, 30.8f, 37.875f, 33.5f, 35.625f)
            curveTo(36.2f, 33.375f, 37.883f, 30.733f, 38.55f, 27.7f)
            curveTo(37.717f, 28.067f, 36.822f, 28.342f, 35.867f, 28.525f)
            curveTo(34.911f, 28.708f, 33.956f, 28.8f, 33.0f, 28.8f)
            curveTo(29.177f, 28.8f, 25.921f, 27.456f, 23.233f, 24.767f)
            curveTo(20.544f, 22.079f, 19.2f, 18.823f, 19.2f, 15.0f)
            curveTo(19.2f, 14.2f, 19.283f, 13.342f, 19.45f, 12.425f)
            curveTo(19.617f, 11.508f, 19.917f, 10.467f, 20.35f, 9.3f)
            curveTo(17.083f, 10.2f, 14.375f, 12.025f, 12.225f, 14.775f)
            curveTo(10.075f, 17.525f, 9.0f, 20.6f, 9.0f, 24.0f)
            curveTo(9.0f, 28.167f, 10.458f, 31.708f, 13.375f, 34.625f)
            curveTo(16.292f, 37.542f, 19.833f, 39.0f, 24.0f, 39.0f)
            close()
        }
        path(
            fill = SolidColor(colors.strokeColor),
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
            moveTo(38.078f, 18.186f)
            curveTo(38.849f, 17.297f, 39.432f, 15.757f, 39.22f, 13.845f)
            curveTo(39.22f, 12.403f, 38.423f, 10.822f, 37.245f, 8.98f)
            curveTo(36.068f, 7.137f, 35.628f, 6.94f, 33.465f, 4.759f)
            curveTo(31.249f, 6.975f, 30.756f, 7.183f, 29.578f, 9.026f)
            curveTo(28.4f, 10.868f, 27.709f, 12.526f, 27.709f, 13.968f)
            curveTo(27.555f, 15.87f, 28.376f, 17.447f, 29.138f, 18.367f)
            curveTo(30.28f, 19.51f, 31.185f, 20.316f, 33.461f, 20.442f)
            curveTo(35.872f, 20.442f, 37.226f, 19.167f, 38.078f, 18.186f)
            close()
        }
        path(
            fill = SolidColor(colors.strokeColor),
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
            moveTo(38.078f, 18.186f)
            curveTo(38.849f, 17.297f, 39.432f, 15.757f, 39.22f, 13.845f)
            curveTo(39.22f, 12.403f, 38.423f, 10.822f, 37.245f, 8.98f)
            curveTo(36.068f, 7.137f, 35.628f, 6.94f, 33.465f, 4.759f)
            curveTo(31.249f, 6.975f, 30.756f, 7.183f, 29.578f, 9.026f)
            curveTo(28.4f, 10.868f, 27.709f, 12.526f, 27.709f, 13.968f)
            curveTo(27.555f, 15.87f, 28.376f, 17.447f, 29.138f, 18.367f)
            curveTo(30.28f, 19.51f, 31.185f, 20.316f, 33.461f, 20.442f)
            curveTo(35.872f, 20.442f, 37.226f, 19.167f, 38.078f, 18.186f)
            close()
        }
    }
        .build()