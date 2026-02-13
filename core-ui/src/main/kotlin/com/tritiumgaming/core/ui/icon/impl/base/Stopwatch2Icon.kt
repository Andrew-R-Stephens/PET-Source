package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getActionPanVector
import com.tritiumgaming.core.ui.vector.getAnalyticsVector
import com.tritiumgaming.core.ui.vector.getStopwatch2Vector

@Composable
fun Stopwatch2Icon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getStopwatch2Vector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )

}