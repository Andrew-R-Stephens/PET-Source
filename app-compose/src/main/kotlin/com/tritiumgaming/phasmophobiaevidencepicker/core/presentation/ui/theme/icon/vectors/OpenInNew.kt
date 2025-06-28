package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography

private val vector: ImageVector? = null

private fun getVector(groupColors: List<Color>): ImageVector =

    vector ?: Builder(
        name = "OpenInNew",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f
    ).apply {
        path(
            fill = SolidColor(groupColors[0]), stroke = SolidColor(groupColors[0]),
            strokeLineWidth = 30.0f, strokeLineCap = Butt, strokeLineJoin = Round,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(200.0f, 840.0f)
            quadToRelative(-33.0f, 0.0f, -56.5f, -23.5f)
            reflectiveQuadTo(120.0f, 760.0f)
            verticalLineToRelative(-560.0f)
            quadToRelative(0.0f, -33.0f, 23.5f, -56.5f)
            reflectiveQuadTo(200.0f, 120.0f)
            horizontalLineToRelative(280.0f)
            verticalLineToRelative(80.0f)
            lineTo(200.0f, 200.0f)
            verticalLineToRelative(560.0f)
            horizontalLineToRelative(560.0f)
            verticalLineToRelative(-280.0f)
            horizontalLineToRelative(80.0f)
            verticalLineToRelative(280.0f)
            quadToRelative(0.0f, 33.0f, -23.5f, 56.5f)
            reflectiveQuadTo(760.0f, 840.0f)
            lineTo(200.0f, 840.0f)
            close()
            moveTo(388.0f, 628.0f)
            lineTo(332.0f, 572.0f)
            lineTo(704.0f, 200.0f)
            lineTo(560.0f, 200.0f)
            verticalLineToRelative(-80.0f)
            horizontalLineToRelative(280.0f)
            verticalLineToRelative(280.0f)
            horizontalLineToRelative(-80.0f)
            verticalLineToRelative(-144.0f)
            lineTo(388.0f, 628.0f)
            close()
        }
    }
        .build()

@Composable
fun OpenInNew(
    modifier: Modifier = Modifier
) {

    Image(
        modifier = modifier,
        imageVector = getVector(
            listOf(
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
            Discord()
        }
    }
}
