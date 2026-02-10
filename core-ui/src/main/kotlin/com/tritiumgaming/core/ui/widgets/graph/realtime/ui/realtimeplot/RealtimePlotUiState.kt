package com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeplot

import kotlin.time.Duration.Companion.seconds

data class RealtimePlotUiState<T>(
    val currentTime: Long = System.currentTimeMillis(),
    val viewportXInterval: Long = 60.seconds.inWholeMilliseconds,
    val viewportYInterval: Float = 400f,
    val yRelative: Float = 0f,
    val taps: List<T>? = null
)
