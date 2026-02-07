package com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer

internal data class VisualizerUiState(
    val alpha: Float,
    val viewportY: Int,
    val viewportX: Long,
    val intervalX: Float,
    val intervalY: Float,
    val samplingXInterval: Long,
)
