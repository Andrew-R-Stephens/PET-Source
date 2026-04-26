package com.tritiumgaming.feature.investigation.ui.tool.operationtimer

data class OperationTimerUiState(
    val startTime: Long = TIME_DEFAULT,
    val remainingTime: String = "",
    val paused: Boolean = true
) {

    companion object {
        const val TIME_DEFAULT = 0L
        const val NEVER = 0L
    }
}