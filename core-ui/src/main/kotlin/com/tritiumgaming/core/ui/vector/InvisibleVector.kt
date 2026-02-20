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

fun getInvisibleVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "InvisibleVector",
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
            moveToRelative(629.0f, 541.0f)
            lineToRelative(-44.0f, -44.0f)
            quadToRelative(26.0f, -71.0f, -27.0f, -118.0f)
            reflectiveQuadToRelative(-115.0f, -24.0f)
            lineToRelative(-44.0f, -44.0f)
            quadToRelative(17.0f, -11.0f, 38.0f, -16.0f)
            reflectiveQuadToRelative(43.0f, -5.0f)
            quadToRelative(71.0f, 0.0f, 120.5f, 49.5f)
            reflectiveQuadTo(650.0f, 460.0f)
            quadToRelative(0.0f, 22.0f, -5.5f, 43.5f)
            reflectiveQuadTo(629.0f, 541.0f)
            moveToRelative(129.0f, 129.0f)
            lineToRelative(-40.0f, -40.0f)
            quadToRelative(49.0f, -36.0f, 85.5f, -80.5f)
            reflectiveQuadTo(857.0f, 460.0f)
            quadToRelative(-50.0f, -111.0f, -150.0f, -175.5f)
            reflectiveQuadTo(490.0f, 220.0f)
            quadToRelative(-42.0f, 0.0f, -86.0f, 8.0f)
            reflectiveQuadToRelative(-69.0f, 19.0f)
            lineToRelative(-46.0f, -47.0f)
            quadToRelative(35.0f, -16.0f, 89.5f, -28.0f)
            reflectiveQuadTo(485.0f, 160.0f)
            quadToRelative(143.0f, 0.0f, 261.5f, 81.5f)
            reflectiveQuadTo(920.0f, 460.0f)
            quadToRelative(-26.0f, 64.0f, -67.0f, 117.0f)
            reflectiveQuadToRelative(-95.0f, 93.0f)
            moveToRelative(58.0f, 226.0f)
            lineTo(648.0f, 731.0f)
            quadToRelative(-35.0f, 14.0f, -79.0f, 21.5f)
            reflectiveQuadToRelative(-89.0f, 7.5f)
            quadToRelative(-146.0f, 0.0f, -265.0f, -81.5f)
            reflectiveQuadTo(40.0f, 460.0f)
            quadToRelative(20.0f, -52.0f, 55.5f, -101.5f)
            reflectiveQuadTo(182.0f, 264.0f)
            lineTo(56.0f, 138.0f)
            lineToRelative(42.0f, -43.0f)
            lineToRelative(757.0f, 757.0f)
            close()
            moveTo(223.0f, 306.0f)
            quadToRelative(-37.0f, 27.0f, -71.5f, 71.0f)
            reflectiveQuadTo(102.0f, 460.0f)
            quadToRelative(51.0f, 111.0f, 153.5f, 175.5f)
            reflectiveQuadTo(488.0f, 700.0f)
            quadToRelative(33.0f, 0.0f, 65.0f, -4.0f)
            reflectiveQuadToRelative(48.0f, -12.0f)
            lineToRelative(-64.0f, -64.0f)
            quadToRelative(-11.0f, 5.0f, -27.0f, 7.5f)
            reflectiveQuadToRelative(-30.0f, 2.5f)
            quadToRelative(-70.0f, 0.0f, -120.0f, -49.0f)
            reflectiveQuadToRelative(-50.0f, -121.0f)
            quadToRelative(0.0f, -15.0f, 2.5f, -30.0f)
            reflectiveQuadToRelative(7.5f, -27.0f)
            close()
            moveTo(412.0f, 506.0f)
        }
    }
    .build()