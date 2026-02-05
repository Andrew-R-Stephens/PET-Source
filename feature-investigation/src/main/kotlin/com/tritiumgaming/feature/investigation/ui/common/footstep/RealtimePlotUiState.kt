package com.tritiumgaming.feature.investigation.ui.common.footstep

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal data class RealtimePlotUiState(
    val currentTime: Long = System.currentTimeMillis(),
    val viewportDuration: Duration = 60.seconds,
    val viewportBPM: Int = 400,
    val bpmRatio: Float = 0f,
    val taps: List<TapRecord>? = null
)
