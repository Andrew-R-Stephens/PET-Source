package com.tritiumgaming.core.ui.icon

import com.tritiumgaming.core.ui.icon.color.IconVectorColors

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.getTranslateVector

@Composable
fun TranslateIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getTranslateVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}