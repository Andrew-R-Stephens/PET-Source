package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getArrowKeyboardUpSingleVector

@Composable
fun ArrowKeyboardUpSingleIcon(
    modifier: Modifier = Modifier,
    color: Color
) {
    Image(
        modifier = modifier,
        imageVector = getArrowKeyboardUpSingleVector(
            colors = IconVectorColors.defaults(
                fillColor = color,
                strokeColor = color
            )
        ),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
