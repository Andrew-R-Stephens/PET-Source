package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.color.IconVectorColors

fun getGridVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "Grid",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 500.0f,
        viewportHeight = 500.0f
    ).apply {
        group {
            path(
                fill = SolidColor(colors.fillColor),
                stroke = SolidColor(colors.strokeColor)
            ) {
                moveTo(0f, 0f)
                lineTo(500f, 0f)
                lineTo(500f, 500f)
                lineTo(0f, 500f)
                close()
            }
        }
        group {
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(29.98f, -0.61f)
                lineTo(29.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.08f,
                strokeLineWidth = 2f
            ) {
                moveTo(51.98f, -0.61f)
                lineTo(51.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.01f,
                strokeLineWidth = 2f
            ) {
                moveTo(73.98f, -0.61f)
                lineTo(73.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(95.98f, -0.61f)
                lineTo(95.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.01f,
                strokeLineWidth = 2f
            ) {
                moveTo(8.32f, -0.61f)
                lineTo(8.32f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.08f,
                strokeLineWidth = 2f
            ) {
                moveTo(117.98f, -0.61f)
                lineTo(117.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.01f,
                strokeLineWidth = 2f
            ) {
                moveTo(139.98f, -0.61f)
                lineTo(139.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(161.98f, -0.61f)
                lineTo(161.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.08f,
                strokeLineWidth = 2f
            ) {
                moveTo(183.98f, -0.61f)
                lineTo(183.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.01f,
                strokeLineWidth = 2f
            ) {
                moveTo(205.98f, -0.61f)
                lineTo(205.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(227.98f, -0.61f)
                lineTo(227.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.08f,
                strokeLineWidth = 2f
            ) {
                moveTo(249.98f, -0.61f)
                lineTo(249.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.01f,
                strokeLineWidth = 2f
            ) {
                moveTo(271.98f, -0.61f)
                lineTo(271.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(293.98f, -0.61f)
                lineTo(293.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.08f,
                strokeLineWidth = 2f
            ) {
                moveTo(315.98f, -0.61f)
                lineTo(315.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.01f,
                strokeLineWidth = 2f
            ) {
                moveTo(337.98f, -0.61f)
                lineTo(337.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(359.98f, -0.61f)
                lineTo(359.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.08f,
                strokeLineWidth = 2f
            ) {
                moveTo(381.98f, -0.61f)
                lineTo(381.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.01f,
                strokeLineWidth = 2f
            ) {
                moveTo(403.98f, -0.61f)
                lineTo(403.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(425.98f, -0.61f)
                lineTo(425.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.08f,
                strokeLineWidth = 2f
            ) {
                moveTo(447.98f, -0.61f)
                lineTo(447.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.01f,
                strokeLineWidth = 2f
            ) {
                moveTo(469.98f, -0.61f)
                lineTo(469.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(491.98f, -0.61f)
                lineTo(491.98f, 500.61f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 26.12f)
                lineTo(-0.63f, 26.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 48.12f)
                lineTo(-0.63f, 48.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 70.12f)
                lineTo(-0.63f, 70.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 92.12f)
                lineTo(-0.63f, 92.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 4.12f)
                lineTo(-0.63f, 4.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 114.12f)
                lineTo(-0.63f, 114.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 136.12f)
                lineTo(-0.63f, 136.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 158.12f)
                lineTo(-0.63f, 158.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 180.12f)
                lineTo(-0.63f, 180.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 202.12f)
                lineTo(-0.63f, 202.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 224.12f)
                lineTo(-0.63f, 224.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 246.12f)
                lineTo(-0.63f, 246.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 268.12f)
                lineTo(-0.63f, 268.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 290.12f)
                lineTo(-0.63f, 290.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 312.12f)
                lineTo(-0.63f, 312.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 334.12f)
                lineTo(-0.63f, 334.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 356.12f)
                lineTo(-0.63f, 356.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 378.12f)
                lineTo(-0.63f, 378.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 400.12f)
                lineTo(-0.63f, 400.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 422.12f)
                lineTo(-0.63f, 422.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 444.12f)
                lineTo(-0.63f, 444.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.04f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 466.12f)
                lineTo(-0.63f, 466.12f)
            }
            path(
                fill = SolidColor(colors.strokeColor),
                fillAlpha = 0f,
                stroke = SolidColor(colors.strokeColor),
                strokeAlpha = 0.1f,
                strokeLineWidth = 2f
            ) {
                moveTo(500.6f, 488.12f)
                lineTo(-0.63f, 488.12f)
            }
        }
    }.build()
