package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getResetCrosshairsVector

@Composable
fun ResetCrosshairsIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Image(
        modifier = modifier,
        imageVector = getResetCrosshairsVector(colors),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )

}

@Preview
@Composable
private fun Preview() {
    CrosshairsIcon(
        modifier = Modifier.size(48.dp),
        colors = IconVectorColors.defaults().copy(
            fillColor = Color.White
        )
    )
}