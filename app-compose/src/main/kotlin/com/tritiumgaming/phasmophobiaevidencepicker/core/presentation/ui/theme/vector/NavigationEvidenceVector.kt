package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.vector

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

fun getNavigationEvidenceVector(groupColors: List<Color>): ImageVector =
    Builder(
        name = "IconNavEvidence",
        defaultWidth = 200.0.dp,
        defaultHeight = 200.0.dp,
        viewportWidth = 300.0f,
        viewportHeight = 300.0f
    ).apply {
        group {
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(162.0f, 0.7f)
                curveToRelative(-28.0f, 4.4f, -50.1f, 16.9f, -67.1f, 38.1f)
                curveToRelative(-10.0f, 12.5f, -17.9f, 30.0f, -20.5f, 45.8f)
                curveToRelative(-1.8f, 11.3f, -1.4f, 31.1f, 0.9f, 40.4f)
                curveToRelative(4.2f, 16.6f, 14.1f, 35.3f, 24.0f, 45.2f)
                curveToRelative(13.9f, 14.0f, 20.0f, 18.3f, 35.1f, 24.8f)
                curveToRelative(13.4f, 5.8f, 16.0f, 6.5f, 29.5f, 8.0f)
                curveToRelative(11.9f, 1.4f, 18.7f, 0.9f, 34.8f, -2.6f)
                curveToRelative(18.8f, -4.0f, 32.3f, -11.4f, 45.5f, -24.7f)
                curveToRelative(9.5f, -9.6f, 14.0f, -15.3f, 19.2f, -25.0f)
                curveToRelative(30.4f, -55.8f, 3.5f, -124.8f, -56.5f, -145.3f)
                curveToRelative(-13.3f, -4.5f, -33.1f, -6.6f, -44.9f, -4.7f)
                close()
                moveTo(196.0f, 14.1f)
                curveToRelative(32.5f, 8.2f, 57.4f, 32.9f, 66.1f, 65.4f)
                curveToRelative(2.8f, 10.4f, 3.1f, 31.6f, 0.6f, 41.7f)
                curveToRelative(-8.9f, 36.0f, -35.2f, 61.7f, -70.7f, 69.0f)
                curveToRelative(-11.7f, 2.5f, -28.4f, 2.0f, -40.2f, -1.1f)
                curveToRelative(-32.7f, -8.5f, -57.6f, -34.3f, -65.4f, -67.7f)
                curveToRelative(-2.7f, -11.2f, -2.4f, -31.0f, 0.5f, -41.8f)
                curveToRelative(9.9f, -37.1f, 38.9f, -62.1f, 78.6f, -68.0f)
                curveToRelative(6.1f, -0.9f, 22.3f, 0.4f, 30.5f, 2.5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(166.0f, 27.1f)
                curveToRelative(-28.2f, 3.2f, -53.8f, 23.7f, -63.0f, 50.4f)
                curveToRelative(-5.2f, 15.2f, -5.5f, 35.3f, -0.6f, 49.9f)
                curveToRelative(8.8f, 26.1f, 30.1f, 45.1f, 57.2f, 51.2f)
                curveToRelative(10.0f, 2.2f, 29.0f, 1.5f, 38.1f, -1.4f)
                curveToRelative(12.0f, -3.8f, 22.1f, -10.0f, 31.9f, -19.7f)
                curveToRelative(8.1f, -8.0f, 9.7f, -10.2f, 14.2f, -19.5f)
                curveToRelative(6.1f, -12.8f, 8.2f, -21.6f, 8.2f, -35.2f)
                curveToRelative(0.0f, -21.0f, -6.9f, -37.6f, -21.9f, -53.1f)
                curveToRelative(-16.5f, -17.1f, -39.8f, -25.3f, -64.1f, -22.6f)
                close()
                moveTo(197.7f, 40.8f)
                curveToRelative(32.5f, 12.0f, 50.0f, 45.0f, 41.6f, 78.7f)
                curveToRelative(-2.8f, 11.4f, -8.0f, 20.2f, -17.3f, 29.6f)
                curveToRelative(-14.3f, 14.5f, -30.5f, 20.8f, -50.0f, 19.6f)
                curveToRelative(-32.9f, -2.0f, -57.9f, -25.6f, -62.1f, -58.7f)
                curveToRelative(-1.6f, -13.3f, 2.0f, -29.6f, 9.2f, -41.6f)
                curveToRelative(6.9f, -11.3f, 20.2f, -22.3f, 32.7f, -27.0f)
                curveToRelative(9.9f, -3.8f, 13.5f, -4.3f, 26.2f, -4.0f)
                curveToRelative(9.8f, 0.3f, 12.7f, 0.8f, 19.7f, 3.4f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(176.9f, 73.4f)
                curveToRelative(-0.2f, 0.2f, -2.0f, 0.6f, -3.9f, 0.9f)
                curveToRelative(-11.1f, 1.7f, -20.7f, 8.0f, -29.8f, 19.4f)
                curveToRelative(-6.7f, 8.5f, -12.3f, 18.8f, -13.7f, 25.2f)
                curveToRelative(-0.6f, 3.0f, -0.5f, 3.2f, 4.7f, 5.5f)
                curveToRelative(18.8f, 8.3f, 43.2f, 8.7f, 55.0f, 1.0f)
                curveToRelative(2.1f, -1.3f, 4.2f, -2.4f, 4.7f, -2.4f)
                curveToRelative(0.5f, -0.0f, 4.3f, -3.6f, 8.4f, -8.0f)
                curveToRelative(7.5f, -8.1f, 15.9f, -22.5f, 17.3f, -29.6f)
                curveToRelative(0.6f, -3.0f, 0.4f, -3.2f, -5.8f, -5.6f)
                curveToRelative(-9.5f, -3.8f, -18.3f, -5.8f, -27.9f, -6.3f)
                curveToRelative(-4.7f, -0.3f, -8.8f, -0.3f, -9.0f, -0.1f)
                close()
                moveTo(183.7f, 84.9f)
                curveToRelative(6.4f, 3.0f, 9.8f, 8.7f, 10.0f, 17.3f)
                curveToRelative(0.2f, 6.3f, -0.1f, 7.1f, -3.1f, 11.0f)
                curveToRelative(-7.5f, 10.0f, -22.3f, 11.3f, -30.3f, 2.6f)
                curveToRelative(-4.4f, -4.7f, -6.1f, -11.6f, -4.7f, -18.6f)
                curveToRelative(0.8f, -4.0f, 6.7f, -10.9f, 10.8f, -12.7f)
                curveToRelative(4.5f, -2.0f, 12.4f, -1.8f, 17.3f, 0.4f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(173.0f, 96.8f)
                curveToRelative(-4.2f, 1.3f, -5.3f, 7.2f, -1.8f, 9.6f)
                curveToRelative(4.7f, 3.3f, 8.8f, 1.1f, 8.8f, -4.7f)
                curveToRelative(0.0f, -3.4f, -3.7f, -6.0f, -7.0f, -4.9f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(66.3f, 43.0f)
                curveToRelative(-15.0f, 2.6f, -28.2f, 6.9f, -30.0f, 9.7f)
                curveToRelative(-2.0f, 3.0f, -1.0f, 6.8f, 2.3f, 8.9f)
                curveToRelative(2.4f, 1.6f, 2.9f, 1.5f, 11.2f, -1.0f)
                curveToRelative(7.5f, -2.3f, 21.2f, -5.5f, 23.4f, -5.6f)
                curveToRelative(0.4f, -0.0f, 2.4f, -3.2f, 4.3f, -7.0f)
                curveToRelative(4.2f, -8.2f, 4.9f, -7.9f, -11.2f, -5.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(60.4f, 69.9f)
                curveToRelative(-19.1f, 6.6f, -36.0f, 18.8f, -47.9f, 34.6f)
                curveToRelative(-10.3f, 13.8f, -11.2f, 16.7f, -5.9f, 20.1f)
                curveToRelative(4.0f, 2.7f, 6.7f, 1.1f, 12.7f, -7.4f)
                curveToRelative(10.4f, -14.8f, 23.8f, -26.0f, 39.1f, -32.7f)
                lineToRelative(8.0f, -3.4f)
                lineToRelative(1.7f, -7.1f)
                curveToRelative(0.9f, -3.8f, 1.3f, -7.0f, 1.0f, -7.0f)
                curveToRelative(-0.3f, -0.0f, -4.3f, 1.3f, -8.7f, 2.9f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(56.6f, 96.4f)
                curveToRelative(-21.6f, 11.3f, -36.7f, 32.1f, -42.0f, 57.9f)
                curveToRelative(-2.7f, 13.2f, -3.7f, 33.1f, -2.6f, 48.4f)
                curveToRelative(1.0f, 13.3f, 8.8f, 55.6f, 11.2f, 60.9f)
                curveToRelative(2.1f, 4.5f, 9.5f, 4.4f, 11.6f, -0.1f)
                curveToRelative(1.0f, -2.2f, 0.6f, -5.2f, -2.3f, -18.3f)
                curveToRelative(-6.3f, -28.7f, -6.9f, -34.0f, -6.9f, -56.2f)
                curveToRelative(0.0f, -29.0f, 2.6f, -41.7f, 11.5f, -56.7f)
                curveToRelative(4.5f, -7.5f, 14.0f, -17.0f, 21.7f, -21.7f)
                lineToRelative(6.2f, -3.9f)
                lineToRelative(0.0f, -6.8f)
                curveToRelative(0.0f, -3.8f, -0.4f, -6.9f, -0.9f, -6.9f)
                curveToRelative(-0.5f, -0.0f, -3.9f, 1.5f, -7.5f, 3.4f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(61.1f, 125.5f)
                curveToRelative(-13.2f, 11.3f, -20.4f, 24.3f, -23.8f, 43.0f)
                curveToRelative(-2.6f, 14.0f, -2.3f, 44.0f, 0.6f, 57.3f)
                curveToRelative(4.3f, 20.5f, 12.2f, 36.8f, 23.3f, 48.4f)
                curveToRelative(9.2f, 9.5f, 14.8f, 11.8f, 18.8f, 7.8f)
                curveToRelative(2.9f, -2.9f, 2.5f, -6.7f, -0.7f, -9.3f)
                curveToRelative(-1.5f, -1.2f, -5.6f, -5.0f, -9.0f, -8.5f)
                curveToRelative(-15.9f, -15.9f, -24.2f, -47.2f, -21.4f, -80.4f)
                curveToRelative(1.2f, -14.3f, 3.0f, -22.6f, 6.6f, -29.9f)
                curveToRelative(2.9f, -6.2f, 8.8f, -14.2f, 12.5f, -17.1f)
                lineToRelative(2.9f, -2.3f)
                lineToRelative(-1.7f, -6.7f)
                curveToRelative(-0.9f, -3.8f, -2.0f, -6.8f, -2.3f, -6.8f)
                curveToRelative(-0.3f, -0.0f, -2.9f, 2.0f, -5.8f, 4.5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(71.0f, 155.1f)
                curveToRelative(-6.5f, 8.2f, -7.4f, 13.0f, -6.7f, 35.9f)
                curveToRelative(0.8f, 30.7f, 5.4f, 50.1f, 17.3f, 73.0f)
                curveToRelative(9.1f, 17.6f, 15.9f, 23.5f, 20.7f, 18.2f)
                curveToRelative(2.8f, -3.0f, 2.0f, -6.8f, -2.9f, -13.9f)
                curveToRelative(-14.8f, -21.7f, -22.4f, -50.0f, -22.4f, -83.8f)
                curveToRelative(0.0f, -13.1f, 1.0f, -17.7f, 4.9f, -22.0f)
                curveToRelative(1.9f, -2.0f, 1.8f, -2.1f, -1.1f, -7.3f)
                curveToRelative(-1.6f, -2.8f, -3.6f, -5.2f, -4.4f, -5.2f)
                curveToRelative(-0.7f, -0.0f, -3.2f, 2.3f, -5.4f, 5.1f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(89.0f, 183.0f)
                curveToRelative(0.0f, 13.7f, 0.2f, 15.2f, 2.0f, 17.0f)
                curveToRelative(1.1f, 1.1f, 3.1f, 2.0f, 4.5f, 2.0f)
                curveToRelative(4.9f, -0.0f, 6.5f, -3.1f, 6.5f, -12.3f)
                lineToRelative(-0.1f, -8.2f)
                lineToRelative(-6.4f, -6.8f)
                lineToRelative(-6.5f, -6.7f)
                lineToRelative(0.0f, 15.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(239.3f, 190.2f)
                curveToRelative(-4.1f, 3.0f, -4.2f, 3.2f, -2.8f, 5.8f)
                curveToRelative(1.4f, 2.7f, 1.4f, 2.7f, -5.9f, 6.8f)
                curveToRelative(-7.3f, 4.2f, -9.6f, 5.0f, -9.6f, 3.4f)
                curveToRelative(0.0f, -0.5f, -0.6f, -1.6f, -1.3f, -2.6f)
                curveToRelative(-1.3f, -1.7f, -1.5f, -1.7f, -6.4f, -0.0f)
                lineToRelative(-5.1f, 1.8f)
                lineToRelative(1.9f, 3.8f)
                lineToRelative(2.0f, 3.9f)
                lineToRelative(-3.1f, 1.7f)
                curveToRelative(-1.6f, 0.9f, -3.0f, 1.9f, -3.0f, 2.2f)
                curveToRelative(0.0f, 1.0f, 39.1f, 70.4f, 41.4f, 73.4f)
                curveToRelative(1.3f, 1.8f, 4.7f, 4.4f, 7.6f, 6.0f)
                curveToRelative(16.7f, 9.1f, 37.2f, -0.4f, 41.0f, -19.1f)
                curveToRelative(1.6f, -7.6f, 0.0f, -13.8f, -6.4f, -25.3f)
                curveToRelative(-3.0f, -5.2f, -11.8f, -21.0f, -19.6f, -35.0f)
                curveToRelative(-7.9f, -14.0f, -14.6f, -25.9f, -15.0f, -26.3f)
                curveToRelative(-0.4f, -0.5f, -2.2f, -0.1f, -3.9f, 0.8f)
                curveToRelative(-3.1f, 1.6f, -3.2f, 1.6f, -4.9f, -1.5f)
                curveToRelative(-0.9f, -1.6f, -1.9f, -3.0f, -2.2f, -3.0f)
                curveToRelative(-0.3f, 0.1f, -2.4f, 1.5f, -4.7f, 3.2f)
                close()
                moveTo(268.4f, 237.4f)
                curveToRelative(14.4f, 25.9f, 16.6f, 30.5f, 16.6f, 34.3f)
                curveToRelative(0.0f, 14.8f, -17.4f, 22.5f, -27.7f, 12.2f)
                curveToRelative(-3.1f, -3.0f, -35.7f, -60.8f, -35.2f, -62.4f)
                curveToRelative(0.2f, -0.6f, 6.4f, -4.4f, 13.9f, -8.6f)
                curveToRelative(10.0f, -5.7f, 13.8f, -7.4f, 14.6f, -6.5f)
                curveToRelative(0.7f, 0.6f, 8.6f, 14.6f, 17.8f, 31.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(113.0f, 198.2f)
                curveToRelative(0.0f, 22.4f, 9.1f, 48.3f, 23.0f, 65.6f)
                curveToRelative(3.8f, 4.8f, 5.1f, 5.7f, 8.2f, 6.0f)
                curveToRelative(2.8f, 0.2f, 4.0f, -0.2f, 5.2f, -2.0f)
                curveToRelative(2.5f, -3.5f, 2.0f, -5.7f, -2.7f, -11.7f)
                curveToRelative(-9.3f, -12.0f, -16.4f, -27.6f, -18.7f, -41.3f)
                curveToRelative(-0.6f, -3.5f, -1.2f, -7.4f, -1.4f, -8.8f)
                curveToRelative(-0.3f, -1.4f, -0.5f, -3.7f, -0.5f, -5.1f)
                curveToRelative(-0.1f, -2.2f, -1.3f, -3.4f, -6.2f, -6.3f)
                curveToRelative(-3.3f, -2.0f, -6.2f, -3.6f, -6.5f, -3.6f)
                curveToRelative(-0.2f, -0.0f, -0.4f, 3.3f, -0.4f, 7.2f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(144.0f, 212.9f)
                curveToRelative(0.0f, 14.0f, 5.3f, 27.0f, 14.8f, 37.1f)
                curveToRelative(6.1f, 6.4f, 9.7f, 7.6f, 13.7f, 4.6f)
                curveToRelative(3.5f, -2.5f, 2.4f, -7.0f, -3.4f, -13.1f)
                curveToRelative(-7.0f, -7.4f, -11.1f, -16.3f, -11.8f, -25.8f)
                lineToRelative(-0.6f, -7.4f)
                lineToRelative(-6.3f, -1.4f)
                lineToRelative(-6.4f, -1.5f)
                lineToRelative(0.0f, 7.5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(92.5f, 209.0f)
                curveToRelative(-1.5f, 1.7f, -1.7f, 3.1f, -1.1f, 9.9f)
                curveToRelative(0.7f, 8.8f, 4.1f, 21.7f, 8.1f, 30.7f)
                curveToRelative(5.7f, 13.0f, 18.8f, 29.4f, 23.4f, 29.4f)
                curveToRelative(2.9f, -0.0f, 6.4f, -3.3f, 6.4f, -6.2f)
                curveToRelative(0.0f, -1.7f, -2.1f, -5.1f, -5.6f, -9.4f)
                curveToRelative(-6.4f, -8.0f, -11.8f, -17.6f, -14.7f, -26.0f)
                curveToRelative(-2.6f, -7.2f, -5.0f, -18.4f, -5.0f, -22.7f)
                curveToRelative(0.0f, -1.8f, -0.7f, -4.3f, -1.6f, -5.5f)
                curveToRelative(-2.0f, -2.9f, -7.4f, -3.0f, -9.9f, -0.2f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke =
                    SolidColor(Color(0xFF000000)), strokeLineWidth = 0.0f, strokeLineCap =
                    Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType =
                    NonZero
            ) {
                moveTo(170.0f, 211.7f)
                curveToRelative(0.0f, 2.6f, 6.4f, 17.7f, 9.0f, 21.2f)
                curveToRelative(3.9f, 5.5f, 12.0f, 3.4f, 12.0f, -3.1f)
                curveToRelative(0.0f, -1.4f, -1.3f, -5.0f, -2.8f, -7.9f)
                curveToRelative(-1.6f, -3.0f, -3.3f, -6.9f, -4.0f, -8.7f)
                curveToRelative(-1.1f, -3.2f, -1.2f, -3.2f, -7.6f, -3.2f)
                curveToRelative(-5.6f, -0.0f, -6.6f, 0.3f, -6.6f, 1.7f)
                close()
            }
        }
    }
        .build()