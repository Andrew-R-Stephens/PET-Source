package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.color.IconVectorColors

fun getStoreVector(colors: IconVectorColors = IconVectorColors.defaults()): ImageVector =
    Builder(
        name = "Store",
        defaultWidth = 48.0.dp,
        defaultHeight = 48.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f
    ).apply {

        group {
            path(
                fill = SolidColor(colors.fillColor), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(240.0f, 880.0f)
                quadToRelative(-33.0f, 0.0f, -56.5f, -23.5f)
                reflectiveQuadTo(160.0f, 800.0f)
                verticalLineToRelative(-480.0f)
                quadToRelative(0.0f, -33.0f, 23.5f, -56.5f)
                reflectiveQuadTo(240.0f, 240.0f)
                horizontalLineToRelative(80.0f)
                quadToRelative(0.0f, -66.0f, 47.0f, -113.0f)
                reflectiveQuadToRelative(113.0f, -47.0f)
                quadToRelative(66.0f, 0.0f, 113.0f, 47.0f)
                reflectiveQuadToRelative(47.0f, 113.0f)
                horizontalLineToRelative(80.0f)
                quadToRelative(33.0f, 0.0f, 56.5f, 23.5f)
                reflectiveQuadTo(800.0f, 320.0f)
                verticalLineToRelative(480.0f)
                quadToRelative(0.0f, 33.0f, -23.5f, 56.5f)
                reflectiveQuadTo(720.0f, 880.0f)
                lineTo(240.0f, 880.0f)
                close()
                moveTo(240.0f, 800.0f)
                horizontalLineToRelative(480.0f)
                verticalLineToRelative(-480.0f)
                horizontalLineToRelative(-80.0f)
                verticalLineToRelative(80.0f)
                quadToRelative(0.0f, 17.0f, -11.5f, 28.5f)
                reflectiveQuadTo(600.0f, 440.0f)
                quadToRelative(-17.0f, 0.0f, -28.5f, -11.5f)
                reflectiveQuadTo(560.0f, 400.0f)
                verticalLineToRelative(-80.0f)
                lineTo(400.0f, 320.0f)
                verticalLineToRelative(80.0f)
                quadToRelative(0.0f, 17.0f, -11.5f, 28.5f)
                reflectiveQuadTo(360.0f, 440.0f)
                quadToRelative(-17.0f, 0.0f, -28.5f, -11.5f)
                reflectiveQuadTo(320.0f, 400.0f)
                verticalLineToRelative(-80.0f)
                horizontalLineToRelative(-80.0f)
                verticalLineToRelative(480.0f)
                close()
                moveTo(400.0f, 240.0f)
                horizontalLineToRelative(160.0f)
                quadToRelative(0.0f, -33.0f, -23.5f, -56.5f)
                reflectiveQuadTo(480.0f, 160.0f)
                quadToRelative(-33.0f, 0.0f, -56.5f, 23.5f)
                reflectiveQuadTo(400.0f, 240.0f)
                close()
                moveTo(240.0f, 800.0f)
                verticalLineToRelative(-480.0f)
                verticalLineToRelative(480.0f)
                close()
            }
        }
    }.build()
