package com.tritiumgaming.core.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getActionZoomVector


@Composable
fun ActionZoomIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getActionZoomVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
