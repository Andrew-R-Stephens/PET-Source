package com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer

internal data class PointRecord(
    val pX: Long,
    val pY: Float,
    val avg: Float = 0f,
    val weightedAvg: Float = 0f
)
