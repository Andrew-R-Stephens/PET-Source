package com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer

data class PointRecord(
    val pX: Long,
    val pY: Float,
    val avg: Float = 0f,
    val weightedAvg: Float = 0f
)
