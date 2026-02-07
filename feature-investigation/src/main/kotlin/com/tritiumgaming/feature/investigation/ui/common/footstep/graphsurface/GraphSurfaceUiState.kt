package com.tritiumgaming.feature.investigation.ui.common.footstep.graphsurface

import kotlin.time.Duration.Companion.seconds

internal data class GraphSurfaceUiState(
    val xInterval: Float = 10f,
    val yInterval: Float = 10f,
    val viewportX: Long = 60.seconds.inWholeMilliseconds,
    val samplingInterval: Long = 30.seconds.inWholeMilliseconds
)
