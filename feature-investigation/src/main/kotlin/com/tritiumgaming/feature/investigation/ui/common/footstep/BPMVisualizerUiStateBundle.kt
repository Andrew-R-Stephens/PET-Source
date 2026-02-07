package com.tritiumgaming.feature.investigation.ui.common.footstep

import com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer.VisualizerUiState
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphlabels.GraphLabelsUiState
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphsurface.GraphSurfaceUiState
import kotlin.math.ceil
import kotlin.time.Duration.Companion.seconds

internal data class FootstepVisualizerUiStateBundle(
    private val domain: Long = 10.seconds.inWholeMilliseconds,
    private val range: Int = 360,
    private val domainInterval: Float = 10f,
    private val rangeInterval: Float = 120f,
    private val subDomain: Long = 3.seconds.inWholeMilliseconds,
    private val alpha: Float = 0.01f,

    val visualizerUiState: VisualizerUiState = VisualizerUiState(
        alpha = alpha,
        domain = domain,
        range = range,
        domainInterval = domainInterval,
        rangeInterval = rangeInterval,
        subDomain = subDomain
    ),
    val graphSurfaceUiState: GraphSurfaceUiState = GraphSurfaceUiState(
        domain = visualizerUiState.domain,
        domainInterval = visualizerUiState.domainInterval,
        rangeInterval = calcInterval(
            visualizerUiState.range,
            visualizerUiState.rangeInterval
        ),
        subDomain = visualizerUiState.subDomain
    ),
    val graphLabelsXUiState: GraphLabelsUiState = GraphLabelsUiState(
        max = visualizerUiState.domain.toInt(),
        interval = visualizerUiState.domainInterval
    ),
    val graphLabelsYUiState: GraphLabelsUiState = GraphLabelsUiState(
        max = visualizerUiState.range,
        interval = visualizerUiState.rangeInterval
    )
)

private fun calcInterval(
    interval: Int,
    subInterval: Float
): Float {
    val quantizedMax = ceil(subInterval * interval / subInterval)

    val steps = (quantizedMax / subInterval).toInt().toFloat()

    return steps
}