package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.timer

data class TimerUiState(
    internal val startTime: Long = TIME_DEFAULT,
    internal val remainingTime: Long = 0L,
    internal val paused: Boolean = true
) {

    companion object {

        const val TIME_DEFAULT = 0L
        const val NEVER = 0L
        const val FOREVER = 300000L
        const val DEFAULT = 300000L

    }
}
