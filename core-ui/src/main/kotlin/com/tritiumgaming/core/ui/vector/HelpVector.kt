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

fun getHelpVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
    name = "HelpIcon",
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
        moveTo(511.0f, 702.0f)
        quadToRelative(11.0f, -11.0f, 11.0f, -27.0f)
        reflectiveQuadToRelative(-11.0f, -27.0f)
        quadToRelative(-11.0f, -11.0f, -27.0f, -11.0f)
        reflectiveQuadToRelative(-27.0f, 11.0f)
        quadToRelative(-11.0f, 11.0f, -11.0f, 27.0f)
        reflectiveQuadToRelative(11.0f, 27.0f)
        quadToRelative(11.0f, 11.0f, 27.0f, 11.0f)
        reflectiveQuadToRelative(27.0f, -11.0f)
        close()
        moveTo(449.0f, 567.0f)
        horizontalLineToRelative(59.0f)
        quadToRelative(0.0f, -26.0f, 6.5f, -47.5f)
        reflectiveQuadTo(555.0f, 470.0f)
        quadToRelative(31.0f, -26.0f, 44.0f, -51.0f)
        reflectiveQuadToRelative(13.0f, -55.0f)
        quadToRelative(0.0f, -53.0f, -34.5f, -85.0f)
        reflectiveQuadTo(486.0f, 247.0f)
        quadToRelative(-49.0f, 0.0f, -86.5f, 24.5f)
        reflectiveQuadTo(345.0f, 339.0f)
        lineToRelative(53.0f, 20.0f)
        quadToRelative(11.0f, -28.0f, 33.0f, -43.5f)
        reflectiveQuadToRelative(52.0f, -15.5f)
        quadToRelative(34.0f, 0.0f, 55.0f, 18.5f)
        reflectiveQuadToRelative(21.0f, 47.5f)
        quadToRelative(0.0f, 22.0f, -13.0f, 41.5f)
        reflectiveQuadTo(508.0f, 448.0f)
        quadToRelative(-30.0f, 26.0f, -44.5f, 51.5f)
        reflectiveQuadTo(449.0f, 567.0f)
        close()
        moveTo(480.0f, 880.0f)
        quadToRelative(-82.0f, 0.0f, -155.0f, -31.5f)
        reflectiveQuadToRelative(-127.5f, -86.0f)
        quadTo(143.0f, 708.0f, 111.5f, 635.0f)
        reflectiveQuadTo(80.0f, 480.0f)
        quadToRelative(0.0f, -83.0f, 31.5f, -156.0f)
        reflectiveQuadToRelative(86.0f, -127.0f)
        quadTo(252.0f, 143.0f, 325.0f, 111.5f)
        reflectiveQuadTo(480.0f, 80.0f)
        quadToRelative(83.0f, 0.0f, 156.0f, 31.5f)
        reflectiveQuadTo(763.0f, 197.0f)
        quadToRelative(54.0f, 54.0f, 85.5f, 127.0f)
        reflectiveQuadTo(880.0f, 480.0f)
        quadToRelative(0.0f, 82.0f, -31.5f, 155.0f)
        reflectiveQuadTo(763.0f, 762.5f)
        quadToRelative(-54.0f, 54.5f, -127.0f, 86.0f)
        reflectiveQuadTo(480.0f, 880.0f)
        close()
        moveTo(480.0f, 820.0f)
        quadToRelative(142.0f, 0.0f, 241.0f, -99.5f)
        reflectiveQuadTo(820.0f, 480.0f)
        quadToRelative(0.0f, -142.0f, -99.0f, -241.0f)
        reflectiveQuadToRelative(-241.0f, -99.0f)
        quadToRelative(-141.0f, 0.0f, -240.5f, 99.0f)
        reflectiveQuadTo(140.0f, 480.0f)
        quadToRelative(0.0f, 141.0f, 99.5f, 240.5f)
        reflectiveQuadTo(480.0f, 820.0f)
        close()
        moveTo(480.0f, 480.0f)
        close()
    }
}
.build()