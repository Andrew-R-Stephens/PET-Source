package com.tritiumgaming.core.ui.icon.impl.base


import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getGlobeVector

@Composable
fun GlobeIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getGlobeVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
