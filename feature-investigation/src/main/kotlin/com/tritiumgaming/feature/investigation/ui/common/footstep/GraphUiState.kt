package com.tritiumgaming.feature.investigation.ui.common.footstep

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class GraphUiState(
    val xInterval: Float = 10f,
    val yInterval: Float = 10f,
    val viewportDuration: Duration = 60.seconds,
    val samplingInterval: Duration = 30.seconds
)
