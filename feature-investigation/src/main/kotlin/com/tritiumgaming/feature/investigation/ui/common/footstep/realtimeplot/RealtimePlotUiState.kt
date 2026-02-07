package com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeplot

import com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer.PointRecord
import kotlin.time.Duration.Companion.seconds

internal data class RealtimePlotUiState(
    val currentTime: Long = System.currentTimeMillis(),
    val viewportXInterval: Long = 60.seconds.inWholeMilliseconds,
    val viewportYInterval: Float = 400f,
    val yRelative: Float = 0f,
    val taps: List<PointRecord>? = null
)
