package com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer

data class BpmVisualizerUiActions<T>(
    val onUpdate: (state: T) -> Unit
)