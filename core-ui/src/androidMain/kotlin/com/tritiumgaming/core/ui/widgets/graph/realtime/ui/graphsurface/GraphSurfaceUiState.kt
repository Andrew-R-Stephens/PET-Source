package com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphsurface

import kotlin.time.Duration.Companion.seconds

data class GraphSurfaceUiState(
    val domainInterval: Float = 10f,
    val rangeInterval: Float = 10f,
    val domain: Long = 60.seconds.inWholeMilliseconds,
    val domainSampleInterval: Long = 30.seconds.inWholeMilliseconds
)
