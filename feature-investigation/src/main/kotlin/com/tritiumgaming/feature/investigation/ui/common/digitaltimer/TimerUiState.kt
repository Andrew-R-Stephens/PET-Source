package com.tritiumgaming.feature.investigation.ui.common.digitaltimer

data class TimerUiState(
    val startTime: Long = TIME_DEFAULT,
    val remainingTime: Long = 0L,
    val paused: Boolean = true
) {

    companion object {
        const val TIME_DEFAULT = 0L
        const val NEVER = 0L
        const val DURATION_30_SECONDS = 300000L
        const val DEFAULT = 300000L
    }
}
