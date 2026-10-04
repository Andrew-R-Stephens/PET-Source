package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getGridVector

@Composable
fun GridIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults(),
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null
) {

    Image(
        modifier = modifier,
        imageVector = getGridVector(colors),
        contentDescription = null,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}
