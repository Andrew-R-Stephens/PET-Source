package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getSpeedBBVector
import com.tritiumgaming.core.ui.vector.getSpeedBVector
import com.tritiumgaming.core.ui.vector.getSpeedVector

@Composable
fun SpeedIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getSpeedVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )

}

@Composable
fun SpeedBIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getSpeedBVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )

}

@Composable
fun SpeedBBIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getSpeedBBVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )

}