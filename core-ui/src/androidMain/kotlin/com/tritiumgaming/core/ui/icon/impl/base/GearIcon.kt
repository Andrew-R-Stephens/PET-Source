package com.tritiumgaming.core.ui.icon.impl.base


import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getGearVector

@Composable
fun GearIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {
    Image(
        modifier = modifier,
        imageVector = getGearVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
