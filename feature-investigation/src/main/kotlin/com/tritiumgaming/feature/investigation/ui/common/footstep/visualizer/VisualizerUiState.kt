package com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer

internal data class VisualizerUiState(
    val alpha: Float,
    val domain: Long,
    val range: Int,
    val domainInterval: Float,
    val rangeInterval: Float,
    val subDomain: Long,
)
