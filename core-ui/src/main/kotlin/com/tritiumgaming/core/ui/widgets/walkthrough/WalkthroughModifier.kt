package com.tritiumgaming.core.ui.widgets.walkthrough

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned

/**
 * Registers a UI component as a target for the walkthrough.
 */
fun Modifier.walkthroughTarget(
    state: WalkthroughState,
    id: String,
    shape: Shape = RectangleShape
): Modifier = this.onGloballyPositioned { coordinates ->
    state.registerTarget(id, coordinates, shape)
}
