package com.tritiumgaming.core.ui.icon.impl.base


import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getMarkXFilledVector
import com.tritiumgaming.core.ui.vector.getMarkXVector

@Composable
fun MarkXCircleIcon(
    modifier: Modifier = Modifier,
    filled: Boolean = false,
    color: Color
) {
    val colors = IconVectorColors.defaults(
        fillColor = color,
        strokeColor = color
    )
    val vector = when(filled) {
        true -> getMarkXFilledVector(colors = colors)
        false -> getMarkXVector(colors = colors)
    }

    Image(
        modifier = modifier,
        imageVector = vector,
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
