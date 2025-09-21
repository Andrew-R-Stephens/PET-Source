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

fun getSimpleSanityMedicationVector(colors: IconVectorColors = IconVectorColors.defaults()): ImageVector =
    Builder(
        name = "SimpleSanityMedication",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 300.0f,
        viewportHeight = 300.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(53.0f, 20.5f)
            lineToRelative(0.0f, 16.5f)
            lineToRelative(98.0f, -0.0f)
            lineToRelative(98.0f, -0.0f)
            lineToRelative(0.0f, -16.5f)
            lineToRelative(0.0f, -16.5f)
            lineToRelative(-98.0f, -0.0f)
            lineToRelative(-98.0f, -0.0f)
            lineToRelative(0.0f, 16.5f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFF000000)),
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(58.0f, 54.3f)
            curveToRelative(-1.4f, 0.6f, -3.1f, 1.7f, -3.8f, 2.4f)
            curveToRelative(-0.7f, 0.7f, -1.9f, 1.3f, -2.5f, 1.3f)
            curveToRelative(-2.2f, -0.0f, -11.5f, 10.7f, -12.2f, 14.2f)
            curveToRelative(-0.4f, 1.8f, -1.3f, 3.6f, -1.9f, 4.0f)
            curveToRelative(-1.6f, 1.0f, -1.6f, 197.6f, 0.0f, 198.6f)
            curveToRelative(0.6f, 0.4f, 1.7f, 2.4f, 2.4f, 4.5f)
            curveToRelative(0.6f, 2.1f, 1.8f, 4.1f, 2.5f, 4.3f)
            curveToRelative(0.7f, 0.3f, 1.8f, 1.8f, 2.5f, 3.4f)
            curveToRelative(0.6f, 1.5f, 1.9f, 3.1f, 2.8f, 3.4f)
            curveToRelative(1.8f, 0.6f, 5.4f, 2.7f, 6.2f, 3.7f)
            curveToRelative(2.3f, 2.7f, 10.1f, 2.9f, 97.0f, 2.9f)
            curveToRelative(86.1f, -0.0f, 96.0f, -0.3f, 96.0f, -2.9f)
            curveToRelative(0.0f, -0.3f, 1.5f, -1.2f, 3.3f, -2.0f)
            curveToRelative(4.0f, -1.8f, 8.2f, -6.3f, 10.4f, -11.1f)
            curveToRelative(0.4f, -0.8f, 0.9f, -1.7f, 1.3f, -2.0f)
            curveToRelative(2.7f, -2.2f, 3.0f, -11.0f, 3.0f, -104.1f)
            curveToRelative(0.0f, -91.9f, -0.3f, -102.9f, -2.9f, -102.9f)
            curveToRelative(-0.5f, -0.0f, -1.1f, -1.1f, -1.5f, -2.3f)
            curveToRelative(-0.6f, -2.4f, -9.1f, -11.7f, -10.7f, -11.7f)
            curveToRelative(-0.4f, -0.0f, -1.4f, -0.6f, -2.1f, -1.3f)
            curveToRelative(-3.8f, -3.6f, -6.3f, -3.7f, -97.9f, -3.6f)
            curveToRelative(-65.8f, -0.0f, -90.1f, 0.3f, -91.9f, 1.2f)
            close()
            moveTo(174.0f, 130.5f)
            lineToRelative(0.0f, 20.5f)
            lineToRelative(20.5f, -0.0f)
            lineToRelative(20.5f, -0.0f)
            lineToRelative(0.0f, 24.0f)
            lineToRelative(0.0f, 24.0f)
            lineToRelative(-20.5f, -0.0f)
            lineToRelative(-20.5f, -0.0f)
            lineToRelative(0.0f, 20.5f)
            lineToRelative(0.0f, 20.5f)
            lineToRelative(-23.5f, -0.0f)
            lineToRelative(-23.5f, -0.0f)
            lineToRelative(0.0f, -20.5f)
            lineToRelative(0.0f, -20.5f)
            lineToRelative(-20.5f, -0.0f)
            lineToRelative(-20.5f, -0.0f)
            lineToRelative(0.0f, -24.0f)
            lineToRelative(0.0f, -24.0f)
            lineToRelative(20.5f, -0.0f)
            lineToRelative(20.5f, -0.0f)
            lineToRelative(0.0f, -20.5f)
            lineToRelative(0.0f, -20.5f)
            lineToRelative(23.5f, -0.0f)
            lineToRelative(23.5f, -0.0f)
            lineToRelative(0.0f, 20.5f)
            close()
        }
    }
        .build()