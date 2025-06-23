package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography

private val vector: ImageVector? = null

private fun getVector(groupColors: List<Color>): ImageVector =

    vector ?: Builder(
        name = "Person",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 700.0f,
        viewportHeight = 700.0f
    ).apply {

        group {
            path(
                fill = SolidColor(groupColors[0]), stroke =
                    SolidColor(groupColors[0]), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(190.7f, 173.65f)
                curveTo(193.89f, 160.0f, 198.72f, 147.47f, 205.63f, 135.69f)
                curveTo(214.85f, 119.97f, 225.64f, 105.49f, 239.24f, 93.38f)
                curveTo(261.29f, 73.73f, 286.27f, 59.81f, 315.65f, 53.74f)
                curveTo(333.81f, 49.99f, 352.04f, 50.07f, 370.04f, 51.39f)
                curveTo(396.43f, 53.33f, 420.77f, 62.72f, 442.64f, 78.09f)
                curveTo(464.23f, 93.25f, 481.86f, 111.91f, 495.02f, 134.85f)
                curveTo(505.32f, 152.79f, 511.86f, 171.88f, 514.03f, 192.45f)
                curveTo(517.66f, 226.97f, 514.59f, 260.51f, 497.67f, 291.49f)
                curveTo(487.23f, 310.62f, 474.28f, 328.05f, 456.86f, 341.62f)
                curveTo(448.77f, 347.92f, 440.48f, 353.95f, 431.56f, 360.65f)
                curveTo(435.13f, 361.48f, 438.32f, 362.23f, 441.5f, 362.97f)
                curveTo(457.35f, 366.67f, 473.42f, 369.62f, 488.99f, 374.25f)
                curveTo(507.51f, 379.76f, 525.85f, 386.12f, 543.84f, 393.2f)
                curveTo(564.17f, 401.2f, 584.13f, 410.33f, 601.79f, 423.48f)
                curveTo(614.87f, 433.22f, 624.48f, 446.18f, 633.02f, 459.97f)
                curveTo(642.1f, 474.63f, 646.91f, 490.79f, 649.89f, 507.57f)
                curveTo(650.75f, 512.41f, 650.94f, 517.42f, 650.95f, 522.36f)
                curveTo(651.04f, 555.19f, 651.01f, 588.02f, 651.0f, 620.85f)
                curveTo(650.99f, 637.33f, 637.24f, 651.0f, 620.67f, 651.0f)
                curveTo(440.86f, 651.0f, 261.04f, 651.0f, 81.23f, 651.0f)
                curveTo(64.69f, 651.0f, 51.01f, 637.29f, 51.0f, 620.75f)
                curveTo(50.99f, 587.08f, 51.18f, 553.42f, 50.92f, 519.76f)
                curveTo(50.8f, 504.88f, 54.01f, 490.67f, 59.6f, 477.24f)
                curveTo(65.76f, 462.42f, 74.41f, 448.98f, 85.36f, 436.86f)
                curveTo(99.47f, 421.24f, 117.12f, 411.36f, 135.75f, 402.73f)
                curveTo(157.64f, 392.59f, 179.93f, 383.33f, 203.24f, 377.04f)
                curveTo(223.8f, 371.49f, 244.63f, 366.96f, 265.34f, 361.98f)
                curveTo(267.05f, 361.57f, 268.75f, 361.09f, 270.63f, 360.59f)
                curveTo(265.35f, 357.02f, 260.09f, 353.57f, 254.94f, 349.98f)
                curveTo(233.25f, 334.84f, 216.63f, 315.27f, 203.98f, 292.12f)
                curveTo(195.35f, 276.33f, 189.39f, 259.45f, 187.41f, 241.8f)
                curveTo(185.68f, 226.29f, 186.63f, 210.43f, 187.11f, 194.75f)
                curveTo(187.32f, 187.81f, 189.31f, 180.92f, 190.7f, 173.65f)
                close()
            }
        }
        group {
            path(
                fill = SolidColor(groupColors[1]), stroke =
                    SolidColor(groupColors[1]), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(497.0f, 621.0f)
                curveTo(358.21f, 621.0f, 219.92f, 621.0f, 81.0f, 621.0f)
                curveTo(81.0f, 619.03f, 81.0f, 617.27f, 81.0f, 615.5f)
                curveTo(81.0f, 584.18f, 80.82f, 552.85f, 81.06f, 521.52f)
                curveTo(81.22f, 500.14f, 89.04f, 481.44f, 101.88f, 464.53f)
                curveTo(114.6f, 447.76f, 131.83f, 437.83f, 150.69f, 429.48f)
                curveTo(170.92f, 420.52f, 191.22f, 411.82f, 212.55f, 405.97f)
                curveTo(231.67f, 400.72f, 251.07f, 396.45f, 270.42f, 392.04f)
                curveTo(287.2f, 388.22f, 304.27f, 386.41f, 321.47f, 386.01f)
                curveTo(338.89f, 385.61f, 356.38f, 384.29f, 373.72f, 385.28f)
                curveTo(392.57f, 386.36f, 411.3f, 389.65f, 430.08f, 391.96f)
                curveTo(453.13f, 394.79f, 475.47f, 401.02f, 497.29f, 408.43f)
                curveTo(518.28f, 415.56f, 538.5f, 424.99f, 559.0f, 433.54f)
                curveTo(571.58f, 438.79f, 583.06f, 445.99f, 592.31f, 455.99f)
                curveTo(606.32f, 471.13f, 616.29f, 488.59f, 618.95f, 509.45f)
                curveTo(619.98f, 517.58f, 620.84f, 525.79f, 620.91f, 533.97f)
                curveTo(621.14f, 562.78f, 621.0f, 591.58f, 621.0f, 621.0f)
                curveTo(579.68f, 621.0f, 538.59f, 621.0f, 497.0f, 621.0f)
                moveTo(207.78f, 478.04f)
                curveTo(195.27f, 483.68f, 182.92f, 489.73f, 170.2f, 494.83f)
                curveTo(159.43f, 499.15f, 150.95f, 506.16f, 149.3f, 517.35f)
                curveTo(147.56f, 529.26f, 148.91f, 541.61f, 148.91f, 554.0f)
                curveTo(149.93f, 554.0f, 151.25f, 554.0f, 152.56f, 554.0f)
                curveTo(284.51f, 554.0f, 416.46f, 554.0f, 548.4f, 554.0f)
                curveTo(548.74f, 554.0f, 549.07f, 553.96f, 549.4f, 554.01f)
                curveTo(552.82f, 554.44f, 554.16f, 553.11f, 554.07f, 549.52f)
                curveTo(553.85f, 541.53f, 554.44f, 533.5f, 553.87f, 525.55f)
                curveTo(553.36f, 518.45f, 551.27f, 511.82f, 546.6f, 505.87f)
                curveTo(540.56f, 498.17f, 531.91f, 494.94f, 523.7f, 491.13f)
                curveTo(501.91f, 481.03f, 479.48f, 472.38f, 456.25f, 466.21f)
                curveTo(441.74f, 462.35f, 426.91f, 459.46f, 412.07f, 457.11f)
                curveTo(397.24f, 454.77f, 382.25f, 452.62f, 367.28f, 452.26f)
                curveTo(348.97f, 451.82f, 330.6f, 453.12f, 312.27f, 454.09f)
                curveTo(302.82f, 454.59f, 293.31f, 455.42f, 284.02f, 457.16f)
                curveTo(258.3f, 461.95f, 233.14f, 468.95f, 207.78f, 478.04f)
                close()
            }
            path(
                fill = SolidColor(groupColors[1]), stroke =
                    SolidColor(groupColors[1]), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(370.9f, 82.0f)
                curveTo(390.18f, 84.74f, 407.59f, 91.15f, 423.43f, 101.8f)
                curveTo(445.47f, 116.63f, 462.58f, 135.81f, 473.86f, 159.85f)
                curveTo(479.36f, 171.56f, 482.69f, 184.01f, 484.1f, 197.04f)
                curveTo(487.12f, 224.95f, 484.28f, 251.84f, 470.94f, 276.9f)
                curveTo(462.35f, 293.04f, 451.01f, 307.01f, 436.85f, 318.84f)
                curveTo(423.23f, 330.22f, 408.31f, 338.8f, 391.44f, 344.24f)
                curveTo(373.64f, 349.98f, 355.29f, 351.3f, 336.94f, 349.79f)
                curveTo(316.08f, 348.07f, 296.37f, 341.36f, 278.81f, 329.85f)
                curveTo(253.19f, 313.06f, 234.17f, 290.42f, 223.37f, 261.58f)
                curveTo(216.38f, 242.92f, 214.51f, 223.23f, 216.15f, 203.5f)
                curveTo(218.28f, 177.84f, 227.11f, 154.65f, 243.18f, 134.1f)
                curveTo(256.63f, 116.91f, 272.77f, 103.25f, 292.13f, 93.53f)
                curveTo(304.6f, 87.27f, 317.89f, 82.96f, 331.95f, 82.07f)
                curveTo(342.68f, 81.38f, 353.44f, 81.27f, 364.19f, 81.05f)
                curveTo(366.27f, 81.01f, 368.36f, 81.67f, 370.9f, 82.0f)
                moveTo(417.07f, 228.57f)
                curveTo(419.77f, 220.07f, 419.23f, 211.24f, 417.7f, 202.87f)
                curveTo(414.51f, 185.43f, 405.25f, 170.94f, 390.94f, 160.7f)
                curveTo(368.37f, 144.56f, 343.47f, 142.51f, 319.11f, 155.33f)
                curveTo(299.81f, 165.49f, 287.12f, 181.87f, 283.85f, 204.64f)
                curveTo(280.97f, 224.66f, 285.11f, 242.61f, 297.81f, 258.17f)
                curveTo(319.04f, 284.19f, 354.57f, 291.0f, 383.41f, 274.77f)
                curveTo(401.11f, 264.81f, 412.94f, 249.85f, 417.07f, 228.57f)
                close()
            }
        }
    }
        .build()

@Composable
fun Person(
    modifier: Modifier = Modifier
) {

    Image(
        modifier = modifier,
        imageVector = getVector(
            listOf(
                LocalPalette.current.background.color,
                LocalPalette.current.textFamily.body
            )
        ),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}

@Preview
@Composable
private fun Preview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            Person()
        }
    }
}
