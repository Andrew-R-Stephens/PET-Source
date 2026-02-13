package com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer

import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels.GraphLabelsUiState
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphsurface.GraphSurfaceUiState
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.VisualizerUiState
import kotlin.math.ceil
import kotlin.time.Duration.Companion.seconds

internal data class BpmVisualizerStateBundle(
    private val alpha: Float = 0.01f,
    private val range: Int = 360,
    private val domain: Long = 10.seconds.inWholeMilliseconds,
    private val domainInterval: Float = 10f,
    private val rangeInterval: Float = 120f,
    private val domainSampleInterval: Long = 3.seconds.inWholeMilliseconds,
    val visualizerUiState: VisualizerUiState = VisualizerUiState(
        alpha = alpha,
        range = range,
        domain = domain,
        domainInterval = domainInterval,
        rangeInterval = rangeInterval,
        domainSampleInterval = domainSampleInterval
    ),
    val graphSurfaceUiState: GraphSurfaceUiState = GraphSurfaceUiState(
        domain = visualizerUiState.domain,
        domainInterval = visualizerUiState.domainInterval,
        rangeInterval = calcInterval(
            visualizerUiState.range,
            visualizerUiState.rangeInterval
        ).toFloat(),
        domainSampleInterval = visualizerUiState.domainSampleInterval
    ),
    val graphLabelsXUiState: GraphLabelsUiState = GraphLabelsUiState(
        viewport = visualizerUiState.domain.toInt(),
        interval = visualizerUiState.domainInterval
    ),
    val graphLabelsYUiState: GraphLabelsUiState = GraphLabelsUiState(
        viewport = visualizerUiState.range,
        interval = visualizerUiState.rangeInterval
    )
)

private fun calcInterval(
    interval: Int,
    subInterval: Float
): Int {
    val quantizedMax = ceil(subInterval * interval / subInterval)

    val steps = (quantizedMax / subInterval).toInt()

    return steps
}