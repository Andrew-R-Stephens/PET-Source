package com.tritiumgaming.shared.data.investigation.model

data class SanityTimerData(
    val startTime: Long = TIME_DEFAULT,
    val remainingTime: Long = 0L,
    val paused: Boolean = true
) {
    companion object {
        const val TIME_DEFAULT = 0L
        const val NEVER = 0L
        const val TIME_MIN = 0L
        const val TIME_MAX = 300000L
    }
}
