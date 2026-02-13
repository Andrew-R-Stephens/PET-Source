package com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer

data class BpmVisualizerUiActions<T>(
    val onUpdate: (state: T) -> Unit
)