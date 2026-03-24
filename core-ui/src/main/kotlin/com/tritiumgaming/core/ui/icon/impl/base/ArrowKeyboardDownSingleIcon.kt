package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getArrowKeyboardDownSingleVector

@Composable
fun ArrowKeyboardDownSingleIcon(
    modifier: Modifier = Modifier,
    color: Color
) {
    Image(
        modifier = modifier,
        imageVector = getArrowKeyboardDownSingleVector(
            colors = IconVectorColors.defaults(
                fillColor = color,
                strokeColor = color
            )
        ),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
