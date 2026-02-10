package com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer

data class FootstepVisualizerUiActions<T>(
    val onUpdate: (state: T) -> Unit
)