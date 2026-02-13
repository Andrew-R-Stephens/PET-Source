package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.color.IconVectorColors

fun getConfigVector(
    colors: IconVectorColors
): ImageVector = ImageVector.Builder(
    name = "Config",
    defaultWidth = 200.dp,
    defaultHeight = 200.dp,
    viewportWidth = 960f,
    viewportHeight = 960f
).apply {
    path(fill = SolidColor(Color.White)) {
        moveTo(202f, 800f)
        verticalLineToRelative(-294f)
        horizontalLineToRelative(-82f)
        verticalLineToRelative(-60f)
        horizontalLineToRelative(224f)
        verticalLineToRelative(60f)
        horizontalLineToRelative(-82f)
        verticalLineToRelative(294f)
        horizontalLineToRelative(-60f)
        close()
        moveTo(202f, 386f)
        verticalLineToRelative(-226f)
        horizontalLineToRelative(60f)
        verticalLineToRelative(226f)
        horizontalLineToRelative(-60f)
        close()
        moveTo(368f, 345f)
        verticalLineToRelative(-60f)
        horizontalLineToRelative(82f)
        verticalLineToRelative(-125f)
        horizontalLineToRelative(60f)
        verticalLineToRelative(125f)
        horizontalLineToRelative(82f)
        verticalLineToRelative(60f)
        lineTo(368f, 345f)
        close()
        moveTo(450f, 800f)
        verticalLineToRelative(-395f)
        horizontalLineToRelative(60f)
        verticalLineToRelative(395f)
        horizontalLineToRelative(-60f)
        close()
        moveTo(698f, 800f)
        verticalLineToRelative(-127f)
        horizontalLineToRelative(-82f)
        verticalLineToRelative(-60f)
        horizontalLineToRelative(224f)
        verticalLineToRelative(60f)
        horizontalLineToRelative(-82f)
        verticalLineToRelative(127f)
        horizontalLineToRelative(-60f)
        close()
        moveTo(698f, 553f)
        verticalLineToRelative(-393f)
        horizontalLineToRelative(60f)
        verticalLineToRelative(393f)
        horizontalLineToRelative(-60f)
        close()
    }
}.build()
