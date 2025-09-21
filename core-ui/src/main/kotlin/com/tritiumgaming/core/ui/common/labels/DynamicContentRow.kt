package com.tritiumgaming.core.ui.common.labels


import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import kotlin.math.roundToInt

@Composable
fun DynamicContentRow (
    modifier: Modifier = Modifier,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    maxPercentage: DynamicContentAlignmentPercentage = DynamicContentAlignmentPercentage(),
    startComponent: @Composable () -> Unit,
    endComponent: @Composable () -> Unit,
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints ->

        val startPercentage =
            when(maxPercentage.alignment) {
                Alignment.Start -> maxPercentage.maxPercentage
                Alignment.End -> 1f - maxPercentage.maxPercentage
                else -> .5f
            }

        val startComponentMaxAllowedWidth = (constraints.maxWidth * startPercentage).roundToInt()
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
                y = alignment.align(
                    startComponentPlaceable.height, rowHeight)
            )
            endComponentPlaceable.placeRelative(
                x = constraints.maxWidth - endComponentActualWidth,
                y = alignment.align(
                    endComponentPlaceable.height, rowHeight)
            )
        }
    }
}

data class DynamicContentAlignmentPercentage(
    val maxPercentage: Float = .5f,
    val alignment: Alignment.Horizontal = Alignment.CenterHorizontally
)