package com.tritiumgaming.feature.investigation.ui.common.footstep

import kotlin.time.Duration

data class FootstepVisualizerUiState(
    val alpha: Float,
    val viewportBPM: Int,
    val viewportDuration: Duration,
    val durationSplit: Float,
    val bpmSplit: Float,
    val samplingInterval: Duration,
)
