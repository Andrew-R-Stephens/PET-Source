package com.tritiumgaming.core.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getMarkCheckVector

@Composable
fun MarkCheckIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getMarkCheckVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
