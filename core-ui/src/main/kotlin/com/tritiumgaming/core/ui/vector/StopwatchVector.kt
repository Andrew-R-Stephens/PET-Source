package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.color.IconVectorColors

fun getStopwatchVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =

    Builder(
        name = "Simplification2",
        defaultWidth = 200.dp,
        defaultHeight = 200.dp,
        viewportWidth = 200f,
        viewportHeight = 200f
    ).apply {
        path(fill = SolidColor(colors.fillColor)) {
            moveTo(130.4f, 54.71f)
            curveTo(143.73f, 63.95f, 150.3f, 76.48f, 153.26f, 92.2f)
            curveTo(155.39f, 105.88f, 151.53f, 120.26f, 143.72f, 131.66f)
            curveTo(134.8f, 143.82f, 121.33f, 151.19f, 106.51f, 153.51f)
            curveTo(90.1f, 154.91f, 75.83f, 149.7f, 63.13f, 139.51f)
            curveTo(52.75f, 129.99f, 47.44f, 116.13f, 46.01f, 102.38f)
            curveTo(45.42f, 88.59f, 50.58f, 74.26f, 59.05f, 63.44f)
            curveTo(78.15f, 43.43f, 107.24f, 40.29f, 130.4f, 54.71f)
            close()
        }
        path(fill = SolidColor(colors.strokeColor)) {
            moveTo(92.43f, 0.87f)
            curveTo(93.65f, 0.87f, 94.86f, 0.87f, 96.12f, 0.87f)
            curveTo(97.4f, 0.87f, 98.68f, 0.88f, 100f, 0.88f)
            curveTo(101.92f, 0.87f, 101.92f, 0.87f, 103.88f, 0.87f)
            curveTo(105.71f, 0.87f, 105.71f, 0.87f, 107.57f, 0.87f)
            curveTo(108.69f, 0.87f, 109.82f, 0.87f, 110.98f, 0.88f)
            curveTo(113.57f, 1.01f, 113.57f, 1.01f, 114.57f, 2.01f)
            curveTo(114.73f, 4.52f, 114.81f, 6.98f, 114.82f, 9.48f)
            curveTo(114.85f, 10.18f, 114.87f, 10.88f, 114.89f, 11.6f)
            curveTo(114.9f, 13.63f, 114.9f, 13.63f, 114.57f, 17.09f)
            curveTo(113.58f, 17.75f, 112.58f, 18.41f, 111.56f, 19.1f)
            curveTo(112.06f, 21.09f, 112.06f, 21.09f, 112.56f, 23.12f)
            curveTo(113.58f, 23.37f, 114.6f, 23.63f, 115.65f, 23.89f)
            curveTo(117.01f, 24.24f, 118.36f, 24.59f, 119.72f, 24.94f)
            curveTo(120.39f, 25.1f, 121.06f, 25.27f, 121.75f, 25.44f)
            curveTo(126.03f, 26.55f, 129.76f, 28.02f, 133.67f, 30.15f)
            curveTo(133.59f, 29.4f, 133.5f, 28.66f, 133.42f, 27.89f)
            curveTo(133.67f, 25.13f, 133.67f, 25.13f, 137.69f, 21.11f)
            curveTo(142.66f, 24.09f, 142.66f, 24.09f, 147.74f, 27.14f)
            curveTo(146.68f, 31.35f, 145.81f, 32.38f, 142.71f, 35.18f)
            curveTo(143.27f, 35.59f, 143.83f, 36.01f, 144.41f, 36.43f)
            curveTo(161.43f, 49.31f, 172.58f, 65.98f, 176.26f, 87.19f)
            curveTo(178.74f, 108.07f, 174.47f, 128.66f, 161.81f, 145.73f)
            curveTo(149.29f, 161.09f, 132.86f, 173.56f, 112.56f, 175.88f)
            curveTo(108.5f, 176.07f, 104.44f, 176.11f, 100.38f, 176.13f)
            curveTo(99.31f, 176.15f, 98.24f, 176.17f, 97.13f, 176.19f)
            curveTo(76.53f, 176.29f, 59.4f, 167.42f, 44.54f, 153.58f)
            curveTo(29.9f, 138.25f, 22.4f, 117.82f, 22.87f, 96.7f)
            curveTo(24.63f, 73.69f, 33.82f, 54.56f, 51.26f, 39.2f)
            curveTo(53.23f, 37.69f, 55.14f, 36.42f, 57.29f, 35.18f)
            curveTo(56.46f, 34.41f, 55.63f, 33.64f, 54.77f, 32.85f)
            curveTo(52.26f, 30.15f, 52.26f, 30.15f, 52.26f, 27.14f)
            curveTo(54.46f, 25.52f, 54.46f, 25.52f, 57.29f, 23.93f)
            curveTo(58.22f, 23.4f, 59.15f, 22.87f, 60.11f, 22.32f)
            curveTo(60.84f, 21.92f, 61.56f, 21.52f, 62.31f, 21.11f)
            curveTo(66.27f, 24.45f, 66.27f, 24.45f, 66.58f, 27.89f)
            curveTo(66.5f, 28.64f, 66.42f, 29.38f, 66.33f, 30.15f)
            curveTo(67.1f, 29.81f, 67.86f, 29.47f, 68.65f, 29.12f)
            curveTo(74.89f, 26.43f, 80.75f, 24.34f, 87.44f, 23.12f)
            curveTo(87.77f, 21.79f, 88.1f, 20.46f, 88.44f, 19.1f)
            curveTo(87.45f, 18.43f, 86.45f, 17.77f, 85.43f, 17.09f)
            curveTo(85.18f, 14.46f, 85.1f, 12.1f, 85.18f, 9.48f)
            curveTo(85.18f, 8.78f, 85.19f, 8.08f, 85.19f, 7.36f)
            curveTo(85.29f, 0.73f, 86f, 0.88f, 92.43f, 0.87f)
            close()
            moveTo(91.89f, 37.59f)
            curveTo(73.55f, 40.11f, 59.29f, 49.9f, 48.06f, 64.52f)
            curveTo(38.39f, 79.41f, 35.85f, 96.95f, 39.26f, 114.2f)
            curveTo(43.63f, 130.5f, 53.81f, 144.9f, 68.34f, 153.77f)
            curveTo(85.16f, 162.39f, 102.4f, 164.42f, 120.6f, 158.79f)
            curveTo(130.14f, 155.02f, 137.59f, 150.05f, 144.72f, 142.71f)
            curveTo(145.36f, 142.1f, 146f, 141.48f, 146.65f, 140.85f)
            curveTo(157.85f, 129.26f, 162.35f, 113.21f, 162.16f, 97.43f)
            curveTo(161.57f, 80.88f, 155.16f, 66.54f, 143.55f, 54.9f)
            curveTo(139.51f, 51.16f, 135.38f, 48.04f, 130.65f, 45.23f)
            curveTo(129.52f, 44.52f, 129.52f, 44.52f, 128.36f, 43.79f)
            curveTo(117.48f, 37.64f, 104.13f, 36.22f, 91.89f, 37.59f)
            close()
        }
    }.build()
