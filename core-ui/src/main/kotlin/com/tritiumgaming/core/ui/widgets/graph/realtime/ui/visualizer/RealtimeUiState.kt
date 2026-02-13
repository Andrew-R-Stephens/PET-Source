package com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer

import com.tritiumgaming.core.common.util.datastructs.CircularQueueLinkedList

data class RealtimeUiState<T>(
    val instant: Float = 0f,
    val smoothed: Float = 0f,
    val potential: Float = 0f,
    val recordedTime: Long = 0L,
    val points: CircularQueueLinkedList<T> = CircularQueueLinkedList(0),
    val average: Float = 0f,
    val weightedAverage: Float = 0f,
)
