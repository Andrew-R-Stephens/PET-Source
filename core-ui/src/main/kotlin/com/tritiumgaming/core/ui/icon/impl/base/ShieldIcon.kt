package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getShieldVector

@Composable
fun ShieldIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {
    Image(
        modifier = modifier,
        imageVector = getShieldVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}

@Preview
@Composable
private fun ShieldIconPreview() {
    ShieldIcon(
        modifier = Modifier.padding(16.dp),
        colors = IconVectorColors(
            fillColor = Color.White,
            strokeColor = Color.White
        )
    )
}