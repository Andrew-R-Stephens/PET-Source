package com.tritiumgaming.core.ui.vector

import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.vector.color.IconVectorColors

fun getTruckVector(
    colors: IconVectorColors = IconVectorColors.defaults()
): ImageVector =
    Builder(
        name = "MapTruck",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 400.0f,
        viewportHeight = 400.0f
    ).apply {
        group {
            path(
                fill = SolidColor(colors.strokeColor),
                stroke = SolidColor(colors.strokeColor)
            ) {
                moveTo(254.37f, 88.84f)
                curveTo(255.79f, 97.19f, 256.95f, 105.25f, 258.22f, 114f)
                curveTo(265.49f, 114f, 273.6f, 114f, 281.7f, 114f)
                curveTo(289.03f, 114f, 296.37f, 114.23f, 303.69f, 113.94f)
                curveTo(311.26f, 113.64f, 317.04f, 116.72f, 321.78f, 122.29f)
                curveTo(337.13f, 140.31f, 352.49f, 158.32f, 367.83f, 176.35f)
                curveTo(371.23f, 180.35f, 374.82f, 184.23f, 377.83f, 188.52f)
                curveTo(379.43f, 190.81f, 380.81f, 193.82f, 380.87f, 196.54f)
                curveTo(381.42f, 221.29f, 381.72f, 246.05f, 381.98f, 270.81f)
                curveTo(382.1f, 282.81f, 372.82f, 291.98f, 360.82f, 292f)
                curveTo(357.82f, 292f, 354.83f, 292f, 353.02f, 292f)
                curveTo(347.89f, 298.73f, 344.04f, 305.64f, 338.5f, 310.68f)
                curveTo(324.28f, 323.62f, 304.96f, 328.14f, 286.74f, 319.47f)
                curveTo(275.03f, 313.9f, 266.44f, 305.77f, 261.46f, 293.88f)
                curveTo(260.22f, 290.92f, 258.64f, 289.57f, 255.25f, 289.95f)
                curveTo(220.98f, 293.72f, 186.58f, 291.33f, 152.24f, 292.1f)
                curveTo(150.85f, 292.14f, 148.82f, 293.35f, 148.22f, 294.57f)
                curveTo(141.81f, 307.77f, 131.72f, 316.86f, 117.45f, 319.98f)
                curveTo(102.23f, 323.3f, 87.49f, 321.48f, 75.72f, 310.01f)
                curveTo(70.5f, 304.93f, 66.34f, 298.76f, 60.82f, 292f)
                curveTo(55f, 292f, 47.35f, 292.02f, 39.7f, 292f)
                curveTo(28.43f, 291.97f, 19.01f, 282.58f, 19.01f, 271.33f)
                curveTo(18.99f, 213.85f, 18.99f, 156.38f, 19f, 98.9f)
                curveTo(19.01f, 87.29f, 28.33f, 78.01f, 39.97f, 78f)
                curveTo(105.28f, 78f, 170.59f, 78.01f, 235.89f, 77.99f)
                curveTo(244.05f, 77.99f, 249.94f, 81.76f, 254.37f, 88.84f)
                close()
            }
        }
        group {
            path(
                fill = SolidColor(colors.fillColor),
                stroke = SolidColor(colors.fillColor)
            ) {
                moveTo(60f, 98f)
                curveTo(119.12f, 98f, 177.73f, 98f, 236.68f, 98f)
                curveTo(236.68f, 155.9f, 236.68f, 213.61f, 236.68f, 271.66f)
                curveTo(207.12f, 271.66f, 177.39f, 271.66f, 147.1f, 271.66f)
                curveTo(147.82f, 258.49f, 142.77f, 247.25f, 132.92f, 238.6f)
                curveTo(123.19f, 230.06f, 111.19f, 227.49f, 98.58f, 229.09f)
                curveTo(81.62f, 231.24f, 62.64f, 245.2f, 62.8f, 271.7f)
                curveTo(55.06f, 271.7f, 47.33f, 271.7f, 39.29f, 271.7f)
                curveTo(39.29f, 214.03f, 39.29f, 156.32f, 39.29f, 98f)
                curveTo(45.98f, 98f, 52.74f, 98f, 60f, 98f)
                close()
            }
            path(
                fill = SolidColor(colors.fillColor),
                stroke = SolidColor(colors.fillColor)
            ) {
                moveTo(341.25f, 176.75f)
                curveTo(347.99f, 184.49f, 354.5f, 191.95f, 360.92f, 199.49f)
                curveTo(361.58f, 200.26f, 361.95f, 201.53f, 361.96f, 202.57f)
                curveTo(362.01f, 225.54f, 362f, 248.5f, 362f, 271.73f)
                curveTo(356.64f, 271.73f, 351.57f, 271.73f, 346.11f, 271.73f)
                curveTo(346.72f, 258.7f, 341.95f, 247.71f, 332.46f, 239.12f)
                curveTo(322.99f, 230.56f, 311.39f, 227.81f, 298.82f, 229.01f)
                curveTo(282.03f, 230.6f, 263.02f, 243.58f, 261.6f, 269.79f)
                curveTo(257.66f, 269.79f, 253.7f, 269.79f, 249.37f, 269.79f)
                curveTo(249.37f, 224.86f, 249.37f, 179.8f, 249.37f, 134.23f)
                curveTo(250.49f, 134.17f, 251.91f, 134.01f, 253.33f, 134.01f)
                curveTo(268.33f, 133.99f, 283.34f, 134.48f, 298.3f, 133.77f)
                curveTo(304.47f, 133.48f, 307.51f, 136.58f, 310.75f, 140.38f)
                curveTo(317.88f, 148.71f, 324.93f, 157.1f, 331.98f, 165.5f)
                curveTo(335.03f, 169.13f, 337.99f, 172.82f, 341.25f, 176.75f)
                close()
            }
            path(
                fill = SolidColor(colors.fillColor),
                stroke = SolidColor(colors.fillColor)
            ) {
                moveTo(291.11f, 250.99f)
                curveTo(305.59f, 242.83f, 315.96f, 245.56f, 324.84f, 253.98f)
                curveTo(336.16f, 264.72f, 338.44f, 276.98f, 328.95f, 291.5f)
                curveTo(324.28f, 298.65f, 310.83f, 305.18f, 304.41f, 303.77f)
                curveTo(285.94f, 299.7f, 280.6f, 292.37f, 278.27f, 279.74f)
                curveTo(276.12f, 268.11f, 280.58f, 258f, 291.11f, 250.99f)
                close()
            }
            path(
                fill = SolidColor(colors.fillColor),
                stroke = SolidColor(colors.fillColor)
            ) {
                moveTo(80f, 271.33f)
                curveTo(81.95f, 257.82f, 92.48f, 249.93f, 102.15f, 249.02f)
                curveTo(120.47f, 247.3f, 129.52f, 258.01f, 131.6f, 268.74f)
                curveTo(134.49f, 283.66f, 128.68f, 296.74f, 112.21f, 300.54f)
                curveTo(97.39f, 303.96f, 85.22f, 297.39f, 80.24f, 281.23f)
                curveTo(79.35f, 278.33f, 80.04f, 274.95f, 80f, 271.33f)
                close()
            }
        }
    }.build()
