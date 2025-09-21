package com.tritiumgaming.core.ui.icon

import com.tritiumgaming.core.ui.icon.color.IconVectorColors

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.getOpenInNewVector

@Composable
fun OpenInNewIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getOpenInNewVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
