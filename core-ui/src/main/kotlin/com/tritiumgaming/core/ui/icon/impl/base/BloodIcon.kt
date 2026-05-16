package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getBloodFilledVector
import com.tritiumgaming.core.ui.vector.getBloodVector

@Composable
fun BloodIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults(),
    filled: Boolean = false
) {

    Image(
        modifier = modifier,
        imageVector = if(filled) getBloodFilledVector(colors) else getBloodVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )

}