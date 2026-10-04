package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.vector.color.IconVectorColors

fun getMuteVector(
    colors: IconVectorColors
): ImageVector = ImageVector.Builder(
            name = "Mute",
            defaultWidth = 200.dp,
            defaultHeight = 200.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveToRelative(611f, 637f)
                lineToRelative(-43f, -43f)
                lineToRelative(114f, -113f)
                lineToRelative(-114f, -113f)
                lineToRelative(43f, -43f)
                lineToRelative(113f, 114f)
                lineToRelative(113f, -114f)
                lineToRelative(43f, 43f)
                lineToRelative(-114f, 113f)
                lineToRelative(114f, 113f)
                lineToRelative(-43f, 43f)
                lineToRelative(-113f, -114f)
                lineToRelative(-113f, 114f)
                close()
                moveTo(120f, 600f)
                verticalLineToRelative(-240f)
                horizontalLineToRelative(160f)
                lineToRelative(200f, -200f)
                verticalLineToRelative(640f)
                lineTo(280f, 600f)
                lineTo(120f, 600f)
                close()
                moveTo(420f, 312f)
                lineTo(307f, 420f)
                lineTo(180f, 420f)
                verticalLineToRelative(120f)
                horizontalLineToRelative(127f)
                lineToRelative(113f, 109f)
                verticalLineToRelative(-337f)
                close()
                moveTo(311f, 479f)
                close()
            }
        }.build()
