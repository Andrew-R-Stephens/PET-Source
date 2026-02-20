package com.tritiumgaming.core.ui.icon.impl.base

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.modifier.offsetPercent
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette

@Composable
fun TruckTimeIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors
) {
    val stopwatchBackgroundColor = colors.fillColor
    val onStopwatchBackgroundColor = colors.strokeColor

    var rememberSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val density = LocalDensity.current

    val scale by remember {
        derivedStateOf {
            val baselinePx = with(density) { 48.dp.toPx() }
            if (rememberSize.width > 0) rememberSize.width / baselinePx else 1f
        }
    }

    Box(
        modifier = modifier
            .onSizeChanged {
                rememberSize = it
            }
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {

        TruckIcon(
            modifier = Modifier
                .fillMaxSize(1f)
                .aspectRatio(1f)
                .scale(1.05f)
                .offsetPercent(
                    xPercent = 0f,
                    yPercent = .09f
                ),
            colors = IconVectorColors(
                fillColor = colors.fillColor,
                strokeColor = colors.strokeColor
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize(.52f)
                .aspectRatio(1f)
                .offsetPercent(
                    xPercent = -.45f,
                    yPercent = -.3f
                )
                .drawBehind {
                    drawCircle(
                        color = stopwatchBackgroundColor,
                        center = Offset(size.width * .5f, size.height * .5f),
                        radius = size.minDimension * .49f
                    )
                }
                .border(
                    shape = CircleShape,
                    width = (1.dp * scale),
                    color = onStopwatchBackgroundColor
                ),
            contentAlignment = Alignment.Center
        ) {
            StopwatchIcon(
                modifier = Modifier
                    .fillMaxSize(.8f)
                    .aspectRatio(1f)
                    .drawBehind {
                        drawArc(
                            color = onStopwatchBackgroundColor,
                            useCenter = true,
                            startAngle = 180f,
                            sweepAngle = -270f,
                            topLeft = Offset(size.width * .25f, size.height * .25f),
                            size = Size(size.minDimension * .5f, size.minDimension * .5f)
                        )
                    },
                colors = IconVectorColors(
                    fillColor = Color.Transparent,
                    strokeColor = onStopwatchBackgroundColor
                )
            )

        }

    }
}

@Composable
@Preview
fun TestTruckTimeIcon() {
    SelectiveTheme {
        TruckTimeIcon(
            modifier = Modifier
                .size(128.dp),
            colors = IconVectorColors(
                fillColor = LocalPalette.current.surface,
                strokeColor = LocalPalette.current.onSurface
            )
        )
    }
}