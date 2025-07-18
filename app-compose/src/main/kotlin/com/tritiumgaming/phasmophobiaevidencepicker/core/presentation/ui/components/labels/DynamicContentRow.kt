package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.labels


import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import kotlin.math.roundToInt

@Composable
fun DynamicContentRow (
    modifier: Modifier = Modifier,
    startComponent: @Composable () -> Unit,
    endComponent: @Composable () -> Unit,
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints ->

        val startComponentMaxAllowedWidth = (constraints.maxWidth * 0.5f).roundToInt()
        val startComponentPlaceable = subcompose("startComponent") {
            startComponent()
        }[0].measure(Constraints(maxWidth = startComponentMaxAllowedWidth))

        val startComponentActualWidth = startComponentPlaceable.width

        val endComponentAvailableWidth = constraints.maxWidth - startComponentActualWidth
        val endComponentPlaceable = subcompose("endComponent") {
            endComponent()
        }[0].measure(Constraints(minWidth = 0, maxWidth = endComponentAvailableWidth))

        val endComponentActualWidth = endComponentPlaceable.width

        val rowHeight = maxOf(startComponentPlaceable.height, endComponentPlaceable.height)

        layout(width = constraints.maxWidth, height = rowHeight) {
            startComponentPlaceable.placeRelative(
                x = 0,
                y = Alignment.CenterVertically.align(
                    startComponentPlaceable.height, rowHeight)
            )
            endComponentPlaceable.placeRelative(
                x = constraints.maxWidth - endComponentActualWidth,
                y = Alignment.CenterVertically.align(
                    endComponentPlaceable.height, rowHeight)
            )
        }
    }
}