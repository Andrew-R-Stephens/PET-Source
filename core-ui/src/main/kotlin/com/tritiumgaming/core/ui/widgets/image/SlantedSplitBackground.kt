package com.tritiumgaming.core.ui.widgets.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.tan

@Composable
fun SlantedSplitBackground(
    modifier: Modifier = Modifier,
    vararg contents: @Composable () -> Unit
) {
    Layout(
        modifier = modifier.fillMaxSize(),
        content = {
            contents.forEachIndexed { index, content ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (contents.size > 1 && index > 0) {
                                Modifier.clip(SlantedLeftShape(45f))
                            } else Modifier
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    ) { measurables, constraints ->
        val count = measurables.size
        val h = constraints.maxHeight
        val w = constraints.maxWidth

        val angle = 45.0
        val skew = (h * tan(Math.toRadians(angle / 2.0))).toFloat()
        val baseWidth = w.toFloat() / count

        val placeables = measurables.map { measurable ->
            val itemWidth = (baseWidth + skew).toInt()
            measurable.measure(constraints.copy(minWidth = itemWidth, maxWidth = itemWidth))
        }

        layout(w, h) {
            placeables.forEachIndexed { index, placeable ->
                val xPosition = (index * baseWidth - skew / 2f).toInt()
                placeable.placeRelative(xPosition, 0)
            }
        }
    }
}

class SlantedLeftShape(private val angleDegrees: Float = 45f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val skew = size.height * tan(Math.toRadians(angleDegrees.toDouble() / 2.0).toFloat())

            moveTo(skew, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}
