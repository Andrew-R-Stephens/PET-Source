package com.tritiumgaming.feature.investigation.ui.common.footstep.graphsurface

import kotlin.time.Duration.Companion.seconds

internal data class GraphSurfaceUiState(
    val domain: Long = 60.seconds.inWholeMilliseconds,
    val subDomain: Long = 30.seconds.inWholeMilliseconds,
    val domainInterval: Float = 10f,
    val rangeInterval: Float = 10f,
)
