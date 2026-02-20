package com.tritiumgaming.core.ui.icon.impl.composite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.icon.impl.base.InvisibleIcon
import com.tritiumgaming.core.ui.icon.impl.base.ShieldIcon

@Composable
fun SmudgeBlindnessIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ShieldIcon(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind{
                    drawCircle(
                        color = colors.fillColor,
                        radius = size.minDimension * .05f
                    )
                },
            colors = colors
        )

        InvisibleIcon(
            modifier = Modifier
                .fillMaxSize(.45f),
            colors = colors
        )

    }
}

@Preview
@Composable
private fun PreviewSmudgeBlindnessIcon() {
    SmudgeBlindnessIcon(
        modifier = Modifier
            .size(48.dp),
        colors = IconVectorColors.defaults().copy(
            fillColor = Color.White
        )
    )
}