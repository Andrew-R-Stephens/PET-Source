package com.tritiumgaming.feature.investigation.ui.common.footstep

import com.tritiumgaming.core.common.util.datastructs.CircularQueueLinkedList

data class TapUiState(
    val instantBPM: Float,
    val smoothedBPM: Float,
    val potentialBPM: Float,
    val lastTapTime: Long,
    val taps: CircularQueueLinkedList<TapRecord>
)
