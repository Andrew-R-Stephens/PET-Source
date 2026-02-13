package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getActionPanVector
import com.tritiumgaming.core.ui.vector.getAnalyticsVector

@Composable
fun AnalyticsIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getAnalyticsVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )

}