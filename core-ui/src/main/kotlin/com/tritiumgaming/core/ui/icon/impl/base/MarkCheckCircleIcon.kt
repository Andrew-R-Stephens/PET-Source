package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getMarkCheckFilledVector
import com.tritiumgaming.core.ui.vector.getMarkCheckVector

@Composable
fun MarkCheckCircleIcon(
    modifier: Modifier = Modifier,
    filled: Boolean = false,
    color: Color
) {
    val colors = IconVectorColors.defaults(
        fillColor = color,
        strokeColor = color
    )
    val vector = when(filled) {
        true -> getMarkCheckFilledVector(colors = colors)
        false -> getMarkCheckVector(colors = colors)
    }

    Image(
        modifier = modifier,
        imageVector = vector,
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
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MarkCheckCircleIcon(
                modifier = Modifier
                    .size(48.dp),
                color = LocalPalette.current.onSurface
            )
            MarkXCircleIcon(
                modifier = Modifier.size(48.dp),
                color = LocalPalette.current.onSurface
            )
        }
    }
}
