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
import com.tritiumgaming.core.ui.icon.color.IconVectorColors

fun getPOIVector(colors: IconVectorColors = IconVectorColors.defaults()): ImageVector =
    Builder(
        name = "IconPoi",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 200.0f,
        viewportHeight = 200.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(85.25f, 1.5f)
            curveToRelative(-32.35f, 7.05f, -56.05f, 44.1f, -48.7f, 76.15f)
            curveToRelative(2.7f, 11.8f, 6.15f, 18.15f, 20.9f, 38.65f)
            curveToRelative(6.8f, 9.45f, 15.2f, 21.2f, 18.7f, 26.05f)
            curveToRelative(3.5f, 4.9f, 7.7f, 10.75f, 9.35f, 13.0f)
            curveToRelative(1.65f, 2.3f, 4.6f, 6.5f, 6.5f, 9.4f)
            curveToRelative(5.8f, 8.7f, 10.4f, 8.45f, 16.7f, -1.0f)
            curveToRelative(4.15f, -6.15f, 10.45f, -14.95f, 34.0f, -47.65f)
            curveToRelative(12.05f, -16.7f, 16.25f, -23.75f, 18.7f, -31.3f)
            curveToRelative(15.4f, -47.55f, -27.05f, -93.95f, -76.15f, -83.3f)
            close()
            moveTo(112.05f, 48.7f)
            curveToRelative(6.15f, 4.2f, 8.35f, 9.15f, 7.75f, 17.5f)
            curveToRelative(-1.75f, 26.1f, -39.85f, 25.25f, -39.8f, -0.9f)
            curveToRelative(0.05f, -17.1f, 17.95f, -26.35f, 32.05f, -16.6f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFF)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(57.5f, 154.95f)
            curveToRelative(-25.15f, 5.3f, -33.85f, 12.75f, -28.05f, 24.0f)
            curveToRelative(14.45f, 27.9f, 126.65f, 27.9f, 141.1f, -0.0f)
            curveToRelative(5.85f, -11.4f, -2.95f, -18.8f, -28.55f, -24.0f)
            curveToRelative(-12.4f, -2.55f, -13.45f, -2.5f, -14.85f, 0.6f)
            curveToRelative(-1.5f, 3.3f, -0.75f, 4.45f, 2.8f, 4.45f)
            curveToRelative(10.0f, -0.0f, 31.0f, 8.2f, 34.45f, 13.5f)
            curveToRelative(2.2f, 3.35f, 2.25f, 3.35f, -3.65f, 5.95f)
            curveToRelative(-26.95f, 11.85f, -94.55f, 11.85f, -121.5f, -0.0f)
            curveToRelative(-5.9f, -2.6f, -5.85f, -2.6f, -3.65f, -5.95f)
            curveToRelative(3.5f, -5.35f, 17.3f, -10.9f, 33.3f, -13.45f)
            curveToRelative(7.65f, -1.2f, 7.7f, -1.2f, 5.55f, -4.45f)
            curveToRelative(-2.0f, -3.1f, -4.85f, -3.2f, -16.95f, -0.65f)
            close()
        }
    }
        .build()