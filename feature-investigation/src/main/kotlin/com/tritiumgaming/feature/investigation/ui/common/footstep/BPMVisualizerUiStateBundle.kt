package com.tritiumgaming.feature.investigation.ui.common.footstep

import com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer.VisualizerUiState
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphlabels.GraphLabelsUiState
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphsurface.GraphSurfaceUiState
import kotlin.math.ceil
import kotlin.time.Duration.Companion.seconds

internal data class FootstepVisualizerUiStateBundle(
    private val alpha: Float = 0.01f,
    private val viewportY: Int = 360,
    private val viewportX: Long = 10.seconds.inWholeMilliseconds,
    private val intervalX: Float = 10f,
    private val intervalY: Float = 120f,
    private val samplingXInterval: Long = 3.seconds.inWholeMilliseconds,
    val visualizerUiState: VisualizerUiState = VisualizerUiState(
        alpha = alpha,
        viewportY = viewportY,
        viewportX = viewportX,
        intervalX = intervalX,
        intervalY = intervalY,
        samplingXInterval = samplingXInterval
    ),
    val graphSurfaceUiState: GraphSurfaceUiState = GraphSurfaceUiState(
        xInterval = visualizerUiState.intervalX,
        yInterval = calcInterval(
            visualizerUiState.viewportY,
            visualizerUiState.intervalY
        ).toFloat(),
        viewportX = visualizerUiState.viewportX,
        samplingInterval = visualizerUiState.samplingXInterval
    ),
    val graphLabelsXUiState: GraphLabelsUiState = GraphLabelsUiState(
        viewport = visualizerUiState.viewportX.toInt(),
        interval = visualizerUiState.intervalX
    ),
    val graphLabelsYUiState: GraphLabelsUiState = GraphLabelsUiState(
        viewport = visualizerUiState.viewportY,
        interval = visualizerUiState.intervalY
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