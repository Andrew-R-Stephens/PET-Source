package com.tritiumgaming.core.ui.icon

import com.tritiumgaming.core.ui.icon.color.IconVectorColors

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.getNewsVector

@Composable
fun NewsIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    val vector =

    Image(
        modifier = modifier,
        imageVector = getNewsVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
