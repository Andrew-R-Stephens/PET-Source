package com.tritiumgaming.feature.investigation.ui.common.footstep

internal data class TapRecord(
    val time: Long,
    val bpm: Float,
    val intervalAverage: Float = 0f,
    val intervalWeightedAverage: Float = 0f
)
