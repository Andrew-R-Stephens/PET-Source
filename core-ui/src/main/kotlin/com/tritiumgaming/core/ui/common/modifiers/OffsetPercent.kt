package com.tritiumgaming.core.ui.common.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

fun Modifier.offsetPercent(
    xPercent: Float = 0f,
    yPercent: Float = 0f
) = this.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    val xOffset = (placeable.width * xPercent).toInt()
    val yOffset = (placeable.height * yPercent).toInt()

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(xOffset, yOffset)
    }
}