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
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
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
        },
        measurePolicy = object : MeasurePolicy {
            override fun MeasureScope.measure(
                measurables: List<Measurable>,
                constraints: Constraints
            ): MeasureResult {
                val count = measurables.size
                if (count == 0) {
                    return layout(constraints.minWidth, constraints.minHeight) {}
                }

                val layoutHeight = if (constraints.hasBoundedHeight) constraints.maxHeight else constraints.minHeight
                val layoutWidth = if (constraints.hasBoundedWidth) constraints.maxWidth else constraints.minWidth

                val angle = 45.0
                val skew = (layoutHeight * tan(Math.toRadians(angle / 2.0))).toFloat()
                val baseWidth = layoutWidth.toFloat() / count

                val placeables = measurables.map { measurable ->
                    val itemWidth = (baseWidth + skew).toInt().coerceAtLeast(0)

                    val childConstraints = Constraints(
                        minWidth = itemWidth,
                        maxWidth = itemWidth,
                        minHeight = layoutHeight,
                        maxHeight = layoutHeight
                    )
                    measurable.measure(childConstraints)
                }

                return layout(layoutWidth, layoutHeight) {
                    placeables.forEachIndexed { index, placeable ->
                        val xPosition = (index * baseWidth - skew / 2f).toInt()
                        placeable.placeRelative(xPosition, 0)
                    }
                }
            }

            override fun IntrinsicMeasureScope.minIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int = measurables.maxOfOrNull { it.minIntrinsicHeight(width) } ?: 0

            override fun IntrinsicMeasureScope.maxIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int = measurables.maxOfOrNull { it.maxIntrinsicHeight(width) } ?: 0

            override fun IntrinsicMeasureScope.minIntrinsicWidth(
                measurables: List<IntrinsicMeasurable>,
                height: Int
            ): Int = measurables.maxOfOrNull { it.minIntrinsicWidth(height) } ?: 0

            override fun IntrinsicMeasureScope.maxIntrinsicWidth(
                measurables: List<IntrinsicMeasurable>,
                height: Int
            ): Int = measurables.maxOfOrNull { it.maxIntrinsicWidth(height) } ?: 0
        }
    )
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
