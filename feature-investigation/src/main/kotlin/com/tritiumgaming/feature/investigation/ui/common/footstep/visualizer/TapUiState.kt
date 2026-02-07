package com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer

import com.tritiumgaming.core.common.util.datastructs.CircularQueueLinkedList

internal data class TapUiState(
    val instant: Float = 0f,
    val smoothed: Float = 0f,
    val potential: Float = 0f,
    val recordedTime: Long = 0L,
    val points: CircularQueueLinkedList<PointRecord> = CircularQueueLinkedList(0)
)
