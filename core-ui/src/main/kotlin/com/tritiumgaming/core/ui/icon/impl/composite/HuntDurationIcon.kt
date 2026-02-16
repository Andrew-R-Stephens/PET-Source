package com.tritiumgaming.core.ui.icon.impl.composite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.icon.impl.base.CrosshairsIcon
import com.tritiumgaming.core.ui.icon.impl.base.ShieldIcon
import com.tritiumgaming.core.ui.icon.impl.base.Stopwatch2Icon
import com.tritiumgaming.core.ui.icon.impl.base.StopwatchIcon
import com.tritiumgaming.core.ui.modifier.offsetPercent

@Composable
fun HuntDurationIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    Box(
        modifier = modifier
            .offsetPercent(0f, .05f),
        contentAlignment = Alignment.Center
    ) {
        StopwatchIcon(
            modifier = Modifier
                .fillMaxSize(),
            colors = colors.copy(
                fillColor = Color.Transparent,
                strokeColor = colors.fillColor
            )
        )

        CrosshairsIcon(
            modifier = Modifier
                .fillMaxSize(.5f)
                .drawBehind{
                    drawCircle(
                        color = colors.fillColor,
                        radius = size.minDimension * .05f
                    )
                },
            colors = colors
        )
    }
}

@Preview
@Composable
private fun PreviewHuntDurationIcon() {
    HuntDurationIcon(
        modifier = Modifier
            .size(48.dp),
        colors = IconVectorColors.defaults().copy(
            fillColor = Color.White
        )
    )
}