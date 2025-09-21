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

fun getSanityHeadTopVector(colors: IconVectorColors = IconVectorColors.defaults()): ImageVector =
    Builder(
        name = "IconSanityheadTop",
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
            moveTo(60.99f, 151.0f)
            curveTo(51.82f, 153.26f, 45.64f, 152.53f, 39.21f, 148.49f)
            curveTo(39.58f, 148.19f, 39.94f, 147.89f, 40.3f, 147.6f)
            curveTo(39.35f, 146.31f, 38.13f, 145.14f, 37.51f, 143.71f)
            curveTo(36.75f, 141.98f, 36.04f, 140.02f, 36.08f, 138.18f)
            curveTo(36.25f, 130.6f, 36.71f, 123.02f, 37.03f, 116.2f)
            curveTo(32.13f, 114.23f, 27.28f, 113.04f, 23.45f, 110.36f)
            curveTo(22.02f, 109.37f, 21.85f, 104.21f, 23.12f, 102.24f)
            curveTo(24.99f, 99.34f, 28.22f, 97.47f, 29.26f, 93.59f)
            curveTo(29.7f, 91.94f, 32.88f, 91.21f, 33.94f, 89.51f)
            curveTo(35.66f, 86.75f, 37.38f, 83.64f, 37.84f, 80.49f)
            curveTo(38.7f, 74.51f, 37.94f, 68.24f, 39.22f, 62.38f)
            curveTo(40.4f, 56.96f, 40.41f, 50.38f, 46.93f, 47.43f)
            curveTo(47.52f, 47.16f, 47.93f, 46.16f, 48.13f, 45.41f)
            curveTo(49.79f, 39.28f, 54.87f, 37.71f, 60.09f, 36.25f)
            curveTo(60.58f, 36.11f, 61.35f, 35.49f, 61.33f, 35.14f)
            curveTo(60.87f, 28.86f, 67.29f, 30.49f, 69.89f, 27.69f)
            curveTo(72.84f, 24.53f, 76.01f, 21.66f, 81.21f, 24.1f)
            curveTo(82.43f, 24.67f, 84.87f, 22.09f, 86.9f, 21.64f)
            curveTo(91.78f, 20.54f, 96.79f, 18.99f, 101.68f, 19.2f)
            curveTo(108.76f, 19.51f, 115.85f, 20.87f, 122.77f, 22.48f)
            curveTo(125.41f, 23.09f, 126.71f, 23.0f, 128.88f, 21.32f)
            curveTo(130.11f, 20.38f, 133.35f, 21.34f, 135.34f, 22.23f)
            curveTo(141.07f, 24.78f, 146.44f, 27.8f, 152.57f, 29.84f)
            curveTo(158.77f, 31.91f, 164.09f, 37.74f, 168.76f, 42.87f)
            curveTo(171.54f, 45.92f, 172.33f, 50.79f, 174.02f, 54.84f)
            curveTo(174.4f, 55.73f, 174.56f, 56.87f, 175.22f, 57.46f)
            curveTo(180.6f, 62.3f, 179.36f, 68.54f, 178.97f, 74.6f)
            curveTo(178.72f, 78.35f, 178.56f, 82.14f, 177.94f, 85.84f)
            curveTo(177.21f, 90.19f, 175.55f, 94.43f, 175.22f, 98.78f)
            curveTo(175.03f, 101.23f, 172.83f, 100.92f, 171.92f, 102.65f)
            curveTo(168.1f, 109.97f, 162.74f, 116.47f, 158.17f, 123.41f)
            curveTo(155.76f, 127.08f, 153.83f, 131.05f, 151.77f, 134.72f)
            curveTo(152.06f, 134.96f, 151.4f, 134.41f, 151.29f, 134.32f)
            curveTo(151.29f, 141.17f, 151.2f, 148.22f, 151.32f, 155.27f)
            curveTo(151.41f, 160.5f, 151.83f, 165.73f, 151.97f, 170.97f)
            curveTo(152.05f, 173.97f, 147.22f, 177.13f, 142.82f, 177.68f)
            curveTo(136.83f, 178.42f, 130.89f, 179.58f, 124.93f, 180.52f)
            curveTo(120.92f, 181.16f, 116.9f, 181.73f, 112.65f, 181.58f)
            curveTo(114.02f, 181.04f, 115.38f, 180.51f, 116.79f, 179.96f)
            curveTo(108.89f, 179.96f, 101.21f, 179.96f, 94.41f, 179.96f)
            curveTo(94.12f, 179.79f, 94.83f, 180.21f, 95.54f, 180.62f)
            curveTo(95.41f, 181.08f, 95.27f, 181.54f, 95.14f, 182.0f)
            curveTo(88.51f, 180.23f, 81.72f, 178.9f, 75.36f, 176.45f)
            curveTo(73.05f, 175.57f, 71.09f, 172.24f, 70.19f, 169.61f)
            curveTo(69.12f, 166.45f, 68.84f, 162.8f, 69.11f, 159.43f)
            curveTo(69.58f, 153.53f, 65.91f, 152.03f, 60.99f, 151.0f)
            moveTo(44.0f, 83.14f)
            curveTo(40.97f, 87.63f, 38.01f, 92.18f, 34.85f, 96.58f)
            curveTo(34.15f, 97.57f, 32.34f, 97.84f, 31.79f, 98.85f)
            curveTo(30.61f, 101.05f, 29.59f, 103.46f, 29.21f, 105.9f)
            curveTo(29.08f, 106.75f, 31.07f, 108.88f, 31.98f, 108.82f)
            curveTo(39.99f, 108.22f, 43.66f, 111.39f, 42.31f, 119.63f)
            curveTo(42.19f, 120.34f, 42.91f, 121.14f, 42.96f, 121.92f)
            curveTo(43.01f, 122.89f, 43.14f, 124.28f, 42.59f, 124.79f)
            curveTo(36.52f, 130.46f, 41.76f, 135.59f, 43.36f, 140.77f)
            curveTo(44.44f, 144.25f, 45.21f, 149.05f, 51.06f, 146.05f)
            curveTo(51.51f, 145.83f, 52.3f, 146.36f, 52.93f, 146.42f)
            curveTo(55.02f, 146.62f, 57.61f, 147.65f, 59.11f, 146.79f)
            curveTo(64.47f, 143.74f, 68.31f, 146.42f, 71.92f, 149.6f)
            curveTo(73.27f, 150.79f, 73.64f, 153.1f, 74.47f, 154.9f)
            curveTo(74.04f, 155.1f, 73.61f, 155.31f, 73.18f, 155.52f)
            curveTo(76.74f, 157.78f, 77.27f, 165.19f, 74.17f, 169.44f)
            curveTo(80.34f, 174.73f, 88.16f, 173.73f, 95.39f, 174.97f)
            curveTo(98.93f, 175.58f, 102.6f, 176.65f, 106.07f, 176.25f)
            curveTo(115.57f, 175.16f, 124.98f, 173.58f, 134.55f, 175.72f)
            curveTo(135.52f, 175.94f, 136.8f, 174.27f, 138.06f, 173.83f)
            curveTo(139.71f, 173.25f, 141.48f, 172.99f, 143.21f, 172.66f)
            curveTo(144.7f, 172.37f, 146.21f, 172.18f, 146.82f, 172.09f)
            curveTo(146.82f, 170.57f, 146.67f, 169.6f, 146.85f, 168.7f)
            curveTo(147.09f, 167.48f, 148.04f, 166.26f, 147.9f, 165.13f)
            curveTo(146.96f, 157.36f, 146.14f, 149.63f, 145.72f, 141.77f)
            curveTo(145.14f, 130.88f, 150.55f, 123.54f, 157.21f, 116.43f)
            curveTo(158.19f, 115.38f, 160.07f, 115.16f, 160.96f, 114.8f)
            curveTo(162.26f, 111.71f, 163.75f, 108.57f, 164.92f, 105.32f)
            curveTo(167.05f, 99.45f, 169.16f, 93.57f, 170.94f, 87.59f)
            curveTo(172.0f, 84.05f, 172.37f, 80.32f, 173.12f, 76.68f)
            curveTo(173.7f, 73.86f, 175.02f, 71.01f, 174.82f, 68.26f)
            curveTo(174.6f, 65.3f, 173.31f, 62.27f, 171.88f, 59.6f)
            curveTo(170.81f, 57.61f, 167.88f, 56.39f, 167.3f, 54.38f)
            curveTo(165.39f, 47.74f, 161.1f, 43.96f, 155.16f, 40.8f)
            curveTo(149.9f, 38.01f, 145.46f, 33.71f, 140.57f, 30.19f)
            curveTo(139.89f, 29.7f, 137.97f, 29.58f, 137.89f, 29.79f)
            curveTo(135.82f, 35.03f, 132.08f, 31.55f, 129.91f, 30.68f)
            curveTo(126.67f, 29.38f, 126.94f, 28.75f, 124.5f, 29.13f)
            curveTo(123.8f, 29.25f, 123.06f, 29.2f, 122.35f, 29.1f)
            curveTo(113.25f, 27.75f, 104.14f, 26.37f, 95.04f, 25.02f)
            curveTo(94.52f, 24.94f, 93.97f, 25.1f, 92.79f, 25.2f)
            curveTo(93.92f, 25.82f, 94.39f, 26.08f, 95.45f, 26.67f)
            curveTo(92.97f, 26.81f, 91.43f, 26.71f, 89.98f, 27.01f)
            curveTo(85.67f, 27.93f, 81.53f, 30.67f, 76.91f, 27.66f)
            curveTo(76.35f, 27.3f, 74.58f, 28.8f, 72.81f, 29.73f)
            curveTo(74.96f, 29.98f, 76.3f, 30.13f, 77.38f, 30.25f)
            curveTo(72.12f, 34.09f, 66.91f, 37.91f, 61.68f, 41.7f)
            curveTo(61.44f, 41.88f, 61.04f, 41.99f, 60.77f, 41.92f)
            curveTo(54.31f, 40.26f, 54.05f, 45.76f, 51.95f, 49.34f)
            curveTo(50.55f, 51.72f, 48.48f, 53.71f, 46.89f, 56.0f)
            curveTo(45.72f, 57.69f, 44.61f, 59.5f, 43.91f, 61.42f)
            curveTo(43.33f, 63.03f, 43.16f, 64.88f, 43.21f, 66.61f)
            curveTo(43.37f, 71.84f, 43.72f, 77.07f, 44.0f, 83.14f)
            close()
        }
    }
        .build()