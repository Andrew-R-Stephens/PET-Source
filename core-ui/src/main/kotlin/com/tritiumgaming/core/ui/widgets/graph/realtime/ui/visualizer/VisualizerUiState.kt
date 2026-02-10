package com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer

data class VisualizerUiState(
    val alpha: Float,
    val range: Int,
    val domain: Long,
    val domainInterval: Float,
    val rangeInterval: Float,
    val domainSampleInterval: Long,
)
