package com.tritiumgaming.core.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.getArrow60LeftVector


@Composable
fun Arrow60LeftIcon(
    modifier: Modifier = Modifier,
    tint: List<Color>
) {

    Image(
        modifier = modifier,
        imageVector = getArrow60LeftVector(
            tint
        ),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
